package src.io.controller;

import java.util.HashMap;

import src.Key_Commands;

class KeyRemapper {

    private HashMap<Character, Key_Commands> remap_ = new HashMap<Character, Key_Commands>();

    public KeyRemapper() {
        initBindings();
    }

    private void initBindings() {
        //Character Creation
        remap_.put('Z', Key_Commands.BECOME_SMASHER);
        remap_.put('X', Key_Commands.BECOME_SUMMONER);
        remap_.put('C', Key_Commands.BECOME_SNEAK);
        //Directions NUMPAD
        remap_.put('1', Key_Commands.MOVE_DOWNLEFT);
        remap_.put('2', Key_Commands.MOVE_DOWN);
        remap_.put('3', Key_Commands.MOVE_DOWNRIGHT);
        remap_.put('4', Key_Commands.MOVE_LEFT);
        remap_.put('5', Key_Commands.STANDING_STILL);
        remap_.put('6', Key_Commands.MOVE_RIGHT);
        remap_.put('7', Key_Commands.MOVE_UPLEFT);
        remap_.put('8', Key_Commands.MOVE_UP);
        remap_.put('9', Key_Commands.MOVE_UPRIGHT);
        //Directions Keyboard
        remap_.put('z', Key_Commands.MOVE_DOWNLEFT);
        remap_.put('x', Key_Commands.MOVE_DOWN);
        remap_.put('c', Key_Commands.MOVE_DOWNRIGHT);
        remap_.put('a', Key_Commands.MOVE_LEFT);
        remap_.put('s', Key_Commands.STANDING_STILL);
        remap_.put('d', Key_Commands.MOVE_RIGHT);
        remap_.put('q', Key_Commands.MOVE_UPLEFT);
        remap_.put('w', Key_Commands.MOVE_UP);
        remap_.put('e', Key_Commands.MOVE_UPRIGHT);
        //Interact up bindings.
        remap_.put('p', Key_Commands.PICK_UP_ITEM);
        remap_.put('D', Key_Commands.DROP_LAST_ITEM);
        remap_.put('A', Key_Commands.ATTACK);
        remap_.put('R', Key_Commands.SPEND_SKILLPOINT_ON_BARGAIN);
        remap_.put('E', Key_Commands.EQUIP_LAST_ITEM);
        remap_.put('U', Key_Commands.UNEQUIP_EVERYTHING);
        remap_.put('i', Key_Commands.TOGGLE_VIEW);
        remap_.put('S', Key_Commands.SAVE_GAME);
        remap_.put('u', Key_Commands.USE_LAST_ITEM);
        remap_.put('t', Key_Commands.GET_INTERACTION_OPTIONS);
        //Spend skillpoints
        remap_.put('R', Key_Commands.SPEND_SKILLPOINT_ON_BIND);
        remap_.put('T', Key_Commands.SPEND_SKILLPOINT_ON_BARGAIN);
        remap_.put('Y', Key_Commands.SPEND_SKILLPOINT_ON_OBSERVE);
        remap_.put('F', Key_Commands.SPEND_SKILLPOINT_ON_SKILL_1);
        remap_.put('G', Key_Commands.SPEND_SKILLPOINT_ON_SKILL_2);
        remap_.put('H', Key_Commands.SPEND_SKILLPOINT_ON_SKILL_3);
        remap_.put('V', Key_Commands.SPEND_SKILLPOINT_ON_SKILL_4);
        //Use special abilities
        remap_.put('!', Key_Commands.BIND_WOUNDS);
        remap_.put('@', Key_Commands.BARGAIN_AND_BARTER);
        remap_.put('#', Key_Commands.OBSERVE);
        remap_.put('$', Key_Commands.USE_SKILL_1);
        remap_.put('%', Key_Commands.USE_SKILL_2);
        remap_.put('^', Key_Commands.USE_SKILL_3);
        remap_.put('&', Key_Commands.USE_SKILL_4);
    }

    public void setMap(HashMap<Character, Key_Commands> remap) {
        remap_ = remap;
    }

    public HashMap<Character, Key_Commands> getMap() {
        return remap_;
    }

    public Key_Commands mapInput(char input) {
        Key_Commands command = remap_.get(input);
        if (command == null) {
            return Key_Commands.DO_ABSOLUTELY_NOTHING;
        }
        return command;
    }

    public void bind(char input, Key_Commands command) {
        remap_.put(input, command);
    }
}
