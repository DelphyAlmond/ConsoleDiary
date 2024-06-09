import java.io.*;

public class FileCoder {

    public static void codeToFile(Diary dr) {
        BufferedWriter wr = null;
        try {
            wr = new BufferedWriter(new FileWriter("lastUploadedState.txt"));
            wr.write(dr.datesAndTasks());
            wr.close();
        } catch (IOException e) {
            System.err.println(" < ERROR > Can't write to file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static Diary uploadFromFile() {
        BufferedReader rd = null;
        Diary dr = new Diary(); // String rebuildingInf = "";
        try {
            rd = new BufferedReader(new FileReader("lastUploadedState.txt"));

            // number of lists                              > 3
            // all kol-vs in lists from first to last       > 6 2 5
            //                [ for one date 1 line written ] (out of 3^)
            // date                                         > 8/11
            //                 [ for 1 task 7 lines written ] (out of 6^ of this date)
            // task code (place)                            > 1 (2, 3, 4, ...)
            // task time                                    > 15:40
            // task name                                    > Wash the dishes
            // notes                                        > all notes in 1 line
            // mark ('0' or '1' if completed)               > 1 / 0
            // creation time                                > 12:10
            // time if task was edited                      > 12:35 / -
            // (next batch of tasks for next date <...>)

            Integer numOfDates = Integer.parseInt(rd.readLine());
            String[] kolvoTasks = rd.readLine().split(" ");
            int[] tasksForEachDate = new int[kolvoTasks.length];
            int i = 0;
            for (String num : kolvoTasks)
            {
                tasksForEachDate[i] = Integer.parseInt(num);
                i++;
            }

            for (int k : tasksForEachDate)
            {
                String date = rd.readLine();
                ListOfTasks newDate = new ListOfTasks(date);

                for (int indexT = 0; indexT < k; indexT++)
                {
                    Integer place = Integer.parseInt(rd.readLine());
                    String taskTime = rd.readLine();
                    String task = rd.readLine();
                    String notes = rd.readLine();
                    boolean done = ((Integer.parseInt(rd.readLine()) == 0) ? false : true);
                    String crTime = rd.readLine();
                    String edTime = rd.readLine();

                    TaskLine taskLine = new TaskLine(task, taskTime, crTime);
                    taskLine.setCode(place);
                    taskLine.setNewNotes(notes);
                    if (done) taskLine.setDoneToTrue();
                    if (edTime != "-") taskLine.setEditTime(edTime);

                    newDate.addTask(taskLine);
                }
                dr.addDay(newDate);
            }

            rd.close();
        }
        catch (IOException e) {
            System.err.println(" < ERROR > Can't read from file: " + e.getMessage());
            e.printStackTrace();
        }

        return dr;
    }

    // serialize to .bin
    public static void codeToBinF(Diary dr) {
        try {
            FileOutputStream fo = new FileOutputStream("states\\lastUploadedStateBin");
            ObjectOutputStream out = new ObjectOutputStream(fo);
            out.writeObject(dr);
            out.close();
            fo.close();
            System.out.println(" < SERIALIZED > Data is saved to lastUploadedStateBin.bin ");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // deserialize from .bin
    public static Diary deserializeFromBinF() {
        Diary dr = null;
        try {
            FileInputStream fi = new FileInputStream("states\\lastUploadedStateBin");
            ObjectInputStream in = new ObjectInputStream(fi);
            dr = (Diary) in.readObject();
            in.close();
            fi.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return dr;
    }
}
