/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.io.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import src.Function;
import src.HardCodedStrings;
import src.IO_Bundle;
import src.Key_Commands;
import src.enumHandler;
import src.io.view.AvatarCreationView;
import src.io.view.ChatBoxViewPort;
import src.io.view.MapView;
import src.io.view.StatsView;
import src.io.view.display.Display;
import src.model.map.Map;

/**
 * Uses keyboard input to control the avatar Handles the main game mode
 *
 * @author JohnReedLOL/mbregg
 */
public class GameController extends Controller {

    private final class ChatBoxMiniController implements Function<Void, String> {

        private CommandMiniController commandController_ = new CommandMiniController(getRemapper(), GameController.this);
        private ChatBoxViewPort chatview_ = new ChatBoxViewPort();

        public ChatBoxMiniController() {
            Display.getDisplay().addInputBoxTextEnteredFunction(this);
            Display.getDisplay().addOutputBoxCharacterFunction(new outputBoxFunction());
        }

        /**
         * Processes a command entered into the chatbox. Commands begin with a /
         * Prints the result of running that command to the chatbox.
         *
         * @param foo
         */
        private void processCommandAndDisplayOutput(String foo) {
            Display.getDisplay().setMessage(commandController_.processCommand(foo));
        }

        /**
         * The function that is called by the chat box when enter is hit.
         * Receives contents of input box.
         */
        @Override
        public Void apply(String foo) {
            if (foo.startsWith("/")) {
                processCommandAndDisplayOutput(foo);
                return null;
            }
            //IF it starts with a /, it's a command, so send it
            //To the command function, not the map.
            sendTextCommandAndUpdate(foo);
            return null;
        }

        private Void sendTextCommandAndUpdate(String foo) {
            Key_Commands command = Key_Commands.GET_CONVERSATION_CONTINUATION_OPTIONS;
            if (foo.contains(HardCodedStrings.attack)) {
                command = Key_Commands.ATTACK;
                updateDisplay(sendCommandToMapWithOptionalText(command, ""));
                return null;
            }
            if (foo.contains(HardCodedStrings.getChatOptions)) {
                command = Key_Commands.GET_CONVERSATION_STARTERS;
                updateDisplay(sendCommandToMapWithOptionalText(command, ""));
                return null;
            }
            updateDisplay(sendCommandToMapWithOptionalText(command, foo));
            return null;
        }

        public void chatBoxHandleMapInputAndPrintNewContents(IO_Bundle bundle) {
            chatview_.renderToDisplay(bundle);
            ArrayList<String> list = chatview_.getContents();
            for (String i : list) {
                Display.getDisplay().setMessage(i);
            }
        }

        private class outputBoxFunction implements Function<Void, Character> {

            @Override
            public Void apply(Character foo) {
                sendTextCommandAndUpdate(chatview_.getChoice(Character.getNumericValue(foo)));
                return null;
            }
        }

    }

    public GameController(String uName) {
        super(new AvatarCreationView(), new GameRemapper(), uName);
        Display.getDisplay().setCommandList(HardCodedStrings.gameCommands);
        Display.getDisplay().addDoubleClickCommandEventReceiver(new Function<Void, String>() {

            @Override
            public Void apply(String foo) {
                if (foo == null) {
                    return null;
                }
                Key_Commands command = enumHandler.stringCommandToKeyCommand(foo);
                if (command == null) {
                    return null;
                }
                takeTurnandPrintTurn(command);
                return null;
            }
        });
        takeTurnandPrintTurn('5');//For some reason need to take a empty turn for fonts to load...

    }

    private ChatBoxMiniController chatbox_ = new ChatBoxMiniController();

    /**
     * Takes in a bundle, and updates and then prints the dispaly with it.
     *
     * @param bundle
     */
    @Override
    public void updateDisplay(IO_Bundle bundle) {
        chatbox_.chatBoxHandleMapInputAndPrintNewContents(bundle);
        super.updateDisplay(bundle);
    }

    /**
     * Sends the given command to the map. Focuses on the TextBox for inputting
     * chat options.
     *
     * @param input
     */
    private IO_Bundle sendCommandToMapWithOptionalText(Key_Commands command, String text_or_empty_string) {
        if (command == Key_Commands.GET_INTERACTION_OPTIONS) { // ** This doesn't work for auto-chat **
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    Display.getDisplay().requestOutBoxFocus();
                }
            });
        }
        // final IO_Bundle to_return = MapUserAble_.sendCommandToMapWithOptionalText(getUserName(), command, getView().getWidth() / 2, getView().getHeight() / 2, "");

        final String username = getUserName();
        final String command_enum_as_a_string = command.name();
        final int width_from_center = getView().getWidth() / 2;
        final int height_from_center = getView().getHeight() / 2;
        final String optional_text = text_or_empty_string;

        final String output_to_map_before_trim = (username + " " + command_enum_as_a_string + " " + width_from_center + " " + height_from_center + " " + optional_text);  //.trim();
        // output_to_map.trim()
        // final IO_Bundle to_return = null;
        
        final String output_to_map = output_to_map_before_trim.trim();

        byte[] buf = new byte[256];
        // byte[] buf = null;
        try {
            byte[] buf_temp = output_to_map.getBytes("UTF-8");
            for (int i = 0; i < buf_temp.length; ++i) {
                buf[i] = buf_temp[i];
            }
        } catch (UnsupportedEncodingException unsupportedEncodingException) {
            unsupportedEncodingException.printStackTrace();
            System.exit(-6);
        }
        try {
            // get a datagram socket
            DatagramSocket socket = new DatagramSocket();

            // send request
            InetAddress address = InetAddress.getByName("localhost");
            DatagramPacket packet = new DatagramPacket(buf, buf.length, address, Map.UDP_PORT_NUMBER);
            socket.send(packet); // send UDP to server
            socket.close();
            socket = null;
            System.out.println("udp packet was sent to map");
        } catch (SocketException socket_exception) {
            System.out.println("socket exception in sendCommandToMap(Key_Commands command)");
            socket_exception.printStackTrace();
            System.exit(-76);
        } catch (IOException io_exception) {
            System.out.println("IO exception in sendCommandToMap(Key_Commands command)");
            io_exception.printStackTrace();
        }

        final String hostName = "localhost";
        final int portNumber = Map.TCP_PORT_NUMBER;
        try {
            Socket tcp_socket = new Socket(hostName, portNumber);
            // Socket tcp_socket = new Socket();
            tcp_socket.setTcpNoDelay(true);
            // InetAddress i = InetAddress.getByName(hostName);
            // tcp_socket.bind(new InetSocketAddress(hostName, portNumber));
            // tcp_socket.connect(new InetSocketAddress(hostName, portNumber));
            ObjectInputStream object_input_stream = new ObjectInputStream(tcp_socket.getInputStream());

            try {
                System.out.println("Will crash in GameController?");
                Object object = (IO_Bundle) object_input_stream.readObject();
                System.out.println("Did not crash in GameController.");
                final IO_Bundle to_return_tcp = (IO_Bundle) object;
                object_input_stream.close();
                object_input_stream = null;
                tcp_socket.close();
                tcp_socket = null;
                System.gc(); // socket is gone.
                System.out.println("Definetely did not crash in GameController.");
                
                if (to_return_tcp != null) {
                    System.out.println("to return is not null. ");
                    if (to_return_tcp.occupation_ != null) {
                        System.out.println("to_return.occupation_ is not null.");
                    } else {
                        System.out.println("to_return.occupation_ is null");
                    }
                } else {
                    System.out.println("to_return is null");
                }

                // Make the buttons says the right skill names.
                if ((command == Key_Commands.BECOME_SMASHER || command == Key_Commands.BECOME_SUMMONER
                        || command == Key_Commands.BECOME_SNEAK) && to_return_tcp != null && to_return_tcp.occupation_ != null) {
                    java.awt.EventQueue.invokeLater(new Runnable() {
                        public void run() {
                            Display.getDisplay().getSkillButton(1).
                                    setText(to_return_tcp.occupation_.getSkillNameFromNumber(1));
                            Display.getDisplay().getSkillButton(2).
                                    setText(to_return_tcp.occupation_.getSkillNameFromNumber(2));
                            Display.getDisplay().getSkillButton(3).
                                    setText(to_return_tcp.occupation_.getSkillNameFromNumber(3));
                            Display.getDisplay().getSkillButton(4).
                                    setText(to_return_tcp.occupation_.getSkillNameFromNumber(4));
                        }
                    });
                }
                return to_return_tcp;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                System.out.println("The thing that came out of TCP socket is not an IO_Bundle");
                System.exit(-82);
                return null;
            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Couldn't get I/O for the connection to "
                    + hostName);
            System.exit(-20);
            return null;
        }
    }

    /**
     * Sends the command and string to the map.
     *
     * @param command
     * @param in
     * @return
     */
    //Handles the view switching, uses the  instance of operator in a slightly evil way, 
    //ideally we should look into refactoring this to nots
    protected IO_Bundle updateViewsAndMap(Key_Commands input) {
        boolean taken = false;
        if (getView() instanceof AvatarCreationView) {
            if (Key_Commands.BECOME_SNEAK.equals(input) || Key_Commands.BECOME_SMASHER.equals(input)
                    || Key_Commands.BECOME_SUMMONER.equals(input)) {
                setView(new MapView());
                System.gc();
            }
        }
        if (getView() instanceof MapView) {
            if (Key_Commands.TOGGLE_VIEW.equals(input)) {
                setView(new StatsView(getUserName()));
                System.gc();
                taken = true;
            }
        } else if (getView() instanceof StatsView) {
            if (Key_Commands.TOGGLE_VIEW.equals(input)) {
                setView(new MapView());
                System.gc();
                taken = true;
            }
        }
        if (!taken) {
            return sendCommandToMapWithOptionalText(input, "");
        } else {
            return sendCommandToMapWithOptionalText(Key_Commands.DO_ABSOLUTELY_NOTHING, "");
        }

    }

    @Override
    protected void takeTurnandPrintTurn(Key_Commands input) {
        IO_Bundle bundle = updateViewsAndMap(input);
        updateDisplay(bundle);
    }

    // FIELD ACCESSORS
    /**
     * Gets this UserController's user name value
     * <p>
     * Used for saving. Loading is done through the constructor</p>
     *
     * @return A String object with this UserController's user name
     * @author Alex Stewart
     */
}
