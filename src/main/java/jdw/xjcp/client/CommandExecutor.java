package jdw.xjcp.client;

/**
 * 
 * @author TheDwoon
 * @date 22.09.2014
 * @version 1.0.0
 *
 */
public interface CommandExecutor {
	/**
	 * Called when the command should be executed.
	 * 
	 * @param label the command called.
	 * @param args the arguments provided.
	 */
	void executeCommand(String label, String[] args);
}
