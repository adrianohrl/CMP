package tech.adrianohrl.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
public class FileIO {
    
    /** The constant used to indicate that a file is for reading */
    public final static int FOR_READING = 1;
    /** The constant used to indicate that a file is for writing */
    public final static int FOR_WRITING = 2;
    /** Stores whether the file was intended for writing or for reading */
    private int setting;
    /** The abstracted BufferedReader if the file was for reading */
    private BufferedReader br;
    /** The abstracted PrintWriter if the file was for writing */
    private PrintWriter pw;
    /** Indicates whether the end of the file has been reached or not */
    private boolean eof = false;
    /** Indicates whether the user wanted to extract tokens from each line in the file */
    private boolean tokens;
    /** The delimiter to use if tokens are desired by the user */
    private String delimiter;

    /**
    *  General purpose constructor that can be used to specify a file and whether it is for reading or for writing. <br>
    *  <b>Preconditions</b>:  The file desired (not null) and whether it is for reading or writing must be specified (only options possible).<br>
    *  <b>Postconditions</b>: The specified file is prepared for the desired operation.<br>
    *  <b>Throws</b>: RuntimeException if neither reading nor writing is specified or a problem occurs in preparing the file connections.<br>
     * @param fileName
     * @param operation
    */
    public FileIO(String fileName, int operation) {
        try {
            if (operation == FOR_READING || operation == FOR_WRITING) {
                setting = operation;
            } else {
                throw new RuntimeException("Must specify reading or writing.");
            }
            if (setting == FOR_READING) {
                FileReader fr = new FileReader(fileName);
                br = new BufferedReader(fr);
            } else if (setting == FOR_WRITING) {
                FileWriter fw = new FileWriter(fileName);
                BufferedWriter bw = new BufferedWriter(fw);
                pw = new PrintWriter(bw);
            }
        } catch (IOException ioe) {
            throw new RuntimeException("IO Error");
        }
    }

    /**
    *  Constructor used to read in tokens from a file. <br>
    *  <b>Preconditions</b>:  The file desired (not null) and the delimited desired for extracting tokens must be specified.<br>
    *  <b>Postconditions</b>: The specified file is prepared for reading and token extraction.<br>
    *  <b>Throws</b>: RuntimeException if a problem occurs in preparing the file connections.<br>
     * @param fileName
     * @param delimiter
    */
    public FileIO(String fileName, String delimiter) {
        try {
            setting = FOR_READING;
            tokens = true;
            this.delimiter = delimiter;
            FileReader fr = new FileReader(fileName);
            br = new BufferedReader(fr);
        } catch (IOException ioe) {
            throw new RuntimeException("IO Error");
        }
    }
    
    public FileIO(InputStream in, String delimiter) {
        setting = FOR_READING;
        tokens = true;
        this.delimiter = delimiter;
        this.br = new BufferedReader(new InputStreamReader(in));
    }

    /**
    *  General purpose constructor that can be used to specify a file and a path and whether it is for reading or for writing. <br>
    *  <b>Preconditions</b>:  The file and path desired (not null) and whether it is for reading or writing must be specified (only options possible).<br>
    *  <b>Postconditions</b>: The specified file is prepared for the desired operation.<br>
    *  <b>Throws</b>: RuntimeException if neither reading nor writing is specified or a problem occurs in preparing the file connections.<br>
     * @param fileName
     * @param path
     * @param operation
    */
    public FileIO(String fileName, String path, int operation) {
        try {
            File file = new File(path, fileName);
            if (operation == FOR_READING || operation == FOR_WRITING) {
                setting = operation;
            } else {
                throw new IOException("Must specify reading or writing.");
            }
            if (setting == FOR_READING) {
                FileReader fr = new FileReader(file);
                br = new BufferedReader(fr);
            } else if (setting == FOR_WRITING) {
                FileWriter fw = new FileWriter(file);
                BufferedWriter bw = new BufferedWriter(fw);
                pw = new PrintWriter(bw);
            }
        } catch (IOException ioe) {
            throw new RuntimeException("IO Error");
        }
    }

    /**
    *  Returns the tokens from a single line of text read from a file. <br>
    *  <b>Preconditions</b>: The file must have been opened for reading tokens.<br>
    *  <b>Postconditions</b>: Returns the tokens from a single line of text in the file in an Iterator.<br>
    *  <b>Throws</b>: RuntimeException if the file was not opened for reading or if a problem occurs when reading or if the end of the file has been reached.<br>
     * @return 
    */
    public Iterator<String> getTokens() {
        ArrayList<String> list = new ArrayList<>();
        if (tokens && !eof) {
            String temp = readLine();
            if (!eof) {
                String[] toks = temp.split(delimiter);
                list = new ArrayList<>();
                list.addAll(Arrays.asList(toks));
            }
        } else {
            throw new RuntimeException("Tokens not available.");
        }
        return list.iterator();
    }

    /**
    *  Indicates whether the end of the file has been reached. <br>
    *  <b>Preconditions</b>: None.<br>
    *  <b>Postconditions</b>: Returns true if the end of the file has been reached or false otherwise.<br>
     * @return 
    */
    public boolean eof() {
        return eof;
    }

    /**
    *  Reads in a line of text from a file. <br>
    *  <b>Preconditions</b>: The file must have been opened for reading.<br>
    *  <b>Postconditions</b>: Returns a string with the next line of text from the file or null if the end of the file has been reached.<br>
    *  <b>Throws</b>: RuntimeException if the file was not opened for reading or if a problem occurs when reading from the file.<br>
     * @return 
    */
    public String readLine() {
        String temp = null;
        try {
            if (setting == FOR_READING) {
                temp = br.readLine();
                if (temp == null) {
                    eof = true;
                }
            } else {
                throw new RuntimeException("File is not open for reading.");
            }
        } catch (IOException ioe) {
            throw new RuntimeException("IO Error");
        }
        return temp;
    }

    /**
    *  Writes a line of text to a file. <br>
    *  <b>Preconditions</b>: The file must have been opened for writing, and the line of text to be written supplied (not null).<br>
    *  <b>Postconditions</b>: The line of text is written to the file.<br>
    *  <b>Throws</b>: RuntimeException if the file was not opened for writing or a problem occurs during writing to the file.<br>
     * @param line
    */
    public void writeLine(String line) {
        if (setting == FOR_WRITING) {
            pw.println(line);
        } else {
            throw new RuntimeException("File is not open for writing.");
        }
    }

    /**
    *  Closes the connection to a file. <br>
    *  <b>Preconditions</b>:  None.<br>
    *  <b>Postconditions</b>: The connection to the file is closed.<br>
    *  <b>Throws</b>: RuntimeException if a problem occurs when closing the file.<br>
    */
    public void close() {
        try {
            if (setting == FOR_READING) {
                br.close();
                setting = -1;
                br = null;
            } else if (setting == FOR_WRITING) {
                pw.close();
                setting = -1;
                pw = null;
            }
        } catch (IOException ioe) {
            throw new RuntimeException("IO Error");
        }
    }
}
