import java.util.*;
import java.io.*;

public class DataSource {

  private String delimiter;
  private String currentLine = "";
  private BufferedReader br;
  private File inputFile;

  public DataSource(File inputFile, String delimiter) {
    this.inputFile = inputFile;
    this.delimiter = delimiter;
    init();
  }

  private void init() {
    try {
      br = new BufferedReader(new FileReader(inputFile));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void reset() {
    init();
  }

  public ItemSet next() {
    ItemSet set = new ItemSet();

    if (this.currentLine.length() == 0) {
      String line;
      try {
        line = br.readLine();
        if (line != null) {
          set.setItems(Arrays.asList(line.split(this.delimiter)));
        } else {
          close();
          return null;
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      set.setItems(Arrays.asList(this.currentLine.split(this.delimiter)));
      this.currentLine = "";
      return set;
    }

    return set;
  }
  
  public boolean hasNext() {
    String line;
    try {
      line = br.readLine();
      if (line != null) {
        this.currentLine = line;
      } else {
        close();
        return false;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    return true;
  }

  private void close() {
    try {
      br.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}