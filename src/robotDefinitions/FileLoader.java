package robotDefinitions;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.io.File;

/**
 * A simple, easy-to-use class that reads and writes from a file
 * 
 * @author SecondThread
 */
public class FileLoader {

	/**
	 * Writes the String contents to the file with the name filename. Note that
	 * the file will be cleared before writing.
	 * 
	 * @param filename
	 *            The name of the file
	 * @param contents
	 *            The contents of the file
	 */
	public static void writeToFile(String filename, String contents) {
		if (!filename.endsWith(".txt")) {
			filename+=".txt";
		}
		FileWriter fw=null;
		try {
			fw=new FileWriter(filename);
		} catch (IOException e) {
			e.printStackTrace();
		}
		PrintWriter writer=new PrintWriter(fw);
		writer.print(contents);
		writer.close();
	}

	/**
	 * Reads the file at the specified filename
	 * 
	 * @param filename
	 *            The name of the file that is to be read
	 * @return As array of Strings where each index is the next line of the file
	 */
	public static String[] readFile(String filename) {
		if (!filename.endsWith(".txt")) {
			filename+=".txt";
		}
		LinkedList<String> fileList=new LinkedList<String>();
		FileReader fr=null;
		try {
			fr=new FileReader(filename);
			BufferedReader reader=new BufferedReader(fr);
			while (fileList.peekLast()!=null||fileList.size()==0)
				fileList.add(reader.readLine());
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		fileList.removeLast();
		return (String[])(fileList.toArray(new String[fileList.size()]));
	}

	/**
	 * Checks to see if the file exists
	 * 
	 * @param name
	 *            The name of the file
	 * @return True if the file exists, false otherwise.
	 */
	public static boolean fileExists(String name) {
		return new File(name).exists();
	}
}
