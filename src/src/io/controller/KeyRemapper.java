package src.io.controller;

import java.util.HashMap;

import src.Key_Commands;

abstract class  KeyRemapper {

    private HashMap<Character, Key_Commands> remap_ = new HashMap<Character, Key_Commands>();
    Key_Commands NULL_COMMAND;
    public KeyRemapper() {
        initBindings();
        NULL_COMMAND = Key_Commands.DO_ABSOLUTELY_NOTHING;
    }

    protected abstract void initBindings();
       

    public void setMap(HashMap<Character, Key_Commands> remap) {
        remap_ = remap;
    }

    public HashMap<Character, Key_Commands> getMap() {
        return remap_;
    }

    public Key_Commands
    mapInput(char input) {
        Key_Commands command = remap_.get(input);
        if (command == null) {
            return NULL_COMMAND;
        }
        return command;
    }

    public void bind(char input, Key_Commands command) {
        remap_.put(input, command);
    }
}
