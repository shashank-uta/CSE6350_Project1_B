import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;   

/**
 * This class is the main entry class
 */

public class Database {

    public static Hashtable<Integer, List<Entry>> KeyOffSetPair = new Hashtable<>();

    public static void main(String[] args) throws IOException {


        /***
         * Delete logfile.txt if exists
         * Create a new logfile.txt file
         * */

        File logfile = new File("logfile.txt");

        if (logfile.exists()) {
            logfile.delete();
        } else {
            logfile.createNewFile();
        }

        WriteToFile.writeToFile("      key      ,     value \n");
        File file = new File("input-long.txt");

        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            Scanner scanner = new Scanner(fileInputStream, "UTF-8");
            scanner.nextLine();

            while (scanner.hasNextLine()) {
                String[] columns = scanner.nextLine().split(",");

                /***
                 * To get the hashValue we take columns[1] as argument which is the key in out input-long.txt.
                 * We only consider the last digit to calculate our hashValue
                 *
                 * */
                int hashValue = getValueHash(Integer.parseInt(columns[1].substring(columns[1].length() - 1)));

                String service = columns[0];
                String logFileName = "logfile.txt";

                String getValue = "";
                Integer offSet = null;


                if (service.equals("put")) {
                    offSet = PutKeyValue.put(logFileName, columns[1], columns[3]);
                } else if (service.equals(("get"))) {
                    offSet = getOffSet(columns[1]);
                    if (offSet > 0) {
                        getValue = GetKeyValue.get(logFileName, offSet);
                    }
                } else if (service.equals("del")) {
                    offSet = getOffSet(columns[1]);
                    if (offSet > 0) {
                        DelKey.del(logFileName, offSet);
                    }
                }

                updateHashTable(service, KeyOffSetPair, hashValue, offSet, columns[1]);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static int getValueHash(Integer key) {
        Integer hashVal = null;

        if (key != null) {
            hashVal = key % 10;
        }
        return hashVal;
    }

    /***
     * The getOffSet function returns the Integer OffSet which is the line number
     * our logfile where the key is present
     * */

    public static Integer getOffSet(String key) throws IOException {

        int hashValue = getValueHash(Integer.parseInt(key.substring(key.length() - 1)));

        if (KeyOffSetPair.containsKey(hashValue)) {

            List<Entry> keyOffSet_List = KeyOffSetPair.get(hashValue);

            if (keyOffSet_List != null) {
                return getOffSet(keyOffSet_List, key);
            }
        }

        return 0;

    }

    public static Integer getOffSet(List<Entry> entries, String key) {

        Integer maxOffset = 0;

        for (Entry entry : entries) {
            if (entry.getKey().equals(key) && maxOffset < entry.getOffSet()) {
                maxOffset = entry.getOffSet();
            }
        }

        return maxOffset;
    }

    /***
     * We update the HashTable whenever we encounter "put" or "del" service.
     * Whenever put is encountered we add the KeyOffSetPair at the 'hashValue' index.
     * Whenever del is encountered we delete the KeyOffSetPair at the 'hashValue' index.
     * */

    private static void updateHashTable(String service, Hashtable<Integer, List<Entry>> hashtable, Integer hashValue,
                                        Integer offSet, String key) {

        if (service.equals("put") && offSet != null) {
            Entry entryObj = new Entry(key, offSet);

            if (! hashtable.containsKey(hashValue)) {
                List<Entry> tempList = new ArrayList<>();
                tempList.add(entryObj);
                hashtable.put(hashValue, tempList);
            } else {
                List<Entry> currList = hashtable.get(hashValue);
                currList.add(entryObj);
                hashtable.put(hashValue, currList);
            }

        } else if (service.equals("del") && offSet != null) {

            List<Entry> currList = hashtable.get(hashValue);

            if (currList != null) {
                for (int i = 0; i < currList.size(); i++) {
                    if (key.equals(currList.get(i).getKey())) {
                        currList.remove(i);
                        break;
                    }
                }
                hashtable.put(hashValue, currList);
            }

        }
    }
}

