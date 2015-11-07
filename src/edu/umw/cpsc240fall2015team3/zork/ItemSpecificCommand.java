
package edu.umw.cpsc240fall2015team3.zork;

/**
@author Robert Jamal Washington
*/
/**
A {@link edu.umw.cpsc240fall2015team3.zork.Command} used in conjunction with an {@link edu.umw.cpsc240fall2015team3.zork.Item} that has varying effects depending on the {@link edu.umw.cpsc240fall2015team3.zork.Item} being used.
*/
class ItemSpecificCommand extends Command {

    private String command;
                        
		/**Returns an ItemSpecificCommand} generated by a player's input in the {@link edu.umw.cpsc240fall2015team3.zork.Interpreter}.

		@param command The player's input, which contains an action and the {@link edu.umw.cpsc240fall2015team3.zork.Item} to perform the action on.
		*/
    ItemSpecificCommand(String command) {
        this.command = command;
    }

		/**Performs an action using an {@link edu.umw.cpsc240fall2015team3.zork.Item} that is determined by the player's input, and returns a message describing the action taken. If the player's chosen action cannot be performed on their chosen {@link edu.umw.cpsc240fall2015team3.zork.Item}, return a message that that action cannot be performed on that item. If the player's chosen action is completely unrecognized by the system, return a message that the program cannot interpret the command.
		*/
    public String execute() {
        
        String[] words = command.split(" ");

        // Consider every possible split point in the multi-word command. If
        // any split point corresponds to a verb/noun combo that makes sense,
        // assume that's what the player meant.
        for (int indexOfLastVerb=0; indexOfLastVerb<words.length-1;
            indexOfLastVerb++) {

            String verb = words[0];
            for (int i=1; i<=indexOfLastVerb; i++) {
                verb += " " + words[i];
            }
            String noun = words[indexOfLastVerb+1];
            for (int i=indexOfLastVerb+2; i<words.length; i++) {
                noun += " " + words[i];
            }

            Item itemReferredTo = null;
            try {
                itemReferredTo = 
                    GameState.instance().getItemInVicinityNamed(noun);
            } catch (Item.NoItemException e) {
                continue;  // Just try the next split point.
            }

            String msg = itemReferredTo.getMessageForVerb(verb);
            return (msg == null ? 
                "Sorry, you can't " + verb + " the " + noun + "." : msg) +
                "\n";
        }

        return "Not sure what you mean?\n";
    }
}
