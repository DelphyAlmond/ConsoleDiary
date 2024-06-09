import java.time.LocalTime;
import java.util.Scanner;

public class Main {

    static boolean flag = true;
    static Scanner console = new Scanner(System.in);

    public static void main(String[] args) {

        Diary TaskLists = new Diary();
        boolean correct;

        System.out.println("}*- Diary is empty rn, first of all add (create) new List.");
        int[] DM = dateEnter();
        ListOfTasks currList = new ListOfTasks(DM[0] + "/" + DM[1]);
        TaskLists.addDay(currList);

        while (flag) {

            instructions();

            correct = false;
            String choiceStr = console.nextLine();
            // int choiceInt = console.nextInt();
            // console.nextLine();

            switch (choiceStr) {

                case ("#write") :
                    // register & add exact task
                    int[] HM = timeEnter();
                    if (HM != null) {
                        String time = ((Integer)HM[0]).toString() + ":" + ((Integer)HM[1]).toString();

                        System.out.println("}*- Write a task that has to be done :");
                        System.out.print("\t| Format is [string text] |\n > ");
                        String task = console.nextLine();

                        System.out.println("}*- If you have any extra information, write some notes :");
                        System.out.print("\t| Format is [string text] |\n > ");
                        String txt = console.nextLine();
                        String note = (txt != null && txt.length() > 2) ? txt : null;

                        LocalTime currCreatingTime = LocalTime.now();
                        TaskLine taskObj = new TaskLine(task, time, currCreatingTime.toString());
                        taskObj.setNewNotes(note);

                        // add task to the current day list
                        currList.addTask(taskObj);
                    }
                    correct = true;
                    break;

                case ("#read") :
                    // show all added tasks to current list
                    currList.showList();
                    correct = true;
                    break;

                case("#task") :
                    // redact exact task
                    System.out.println("}*- Enter the index of event / task you would like to edit :");
                    System.out.println("\t| Format is [int place] |");
                    int place = console.nextInt();
                    console.nextLine();

                    if (place > currList.getSize() || place <= 0)
                    {
                        System.out.println("\n< ERROR > This index doesn't exist.");
                        break;
                    }
                    else {
                        TaskLine tskObj = currList.getTaskAt(place);
                        if (tskObj != null) {
                            editingCommands();

                            int command = console.nextInt();
                            console.nextLine();

                            if (command == 1) {
                                int[] newHM = timeEnter();
                                if (newHM != null) {
                                    tskObj.editTime(((Integer) newHM[0]).toString()
                                            + ":" + ((Integer) newHM[1]).toString());
                                }
                                System.out.println("\nTime was updated.");
                            }
                            else if (command == 2) {
                                System.out.print("\nWrite down new note :\n >");
                                tskObj.setNewNotes(console.nextLine());
                            }
                            else if (command == 3) {
                                tskObj.setDoneToTrue();
                                System.out.println("\nThis task was set to [ V ].");
                            }
                            else {
                                currList.deleteTaskAt(place);
                                break;
                            }
                            currList.setUpdatedTask(tskObj, place);

                            LocalTime currEditingTime = LocalTime.now();
                            tskObj.setEditTime(currEditingTime.toString());
                        }
                        else System.out.println("\n< ERROR > Can't extract & edit the task.");
                    }
                    correct = true;
                    break;

                case ("#lists") :
                    TaskLists.showDays();
                    // moving from one date to another
                    System.out.println("}*- Enter the index of date you would like to edit & switch to :");
                    System.out.println("\t| Format is [int place] |");

                    int number = console.nextInt();
                    console.nextLine();

                    if (number <= TaskLists.getSize()) {
                        currList = TaskLists.getDay(number);
                    }
                    correct = true;
                    break;

                case ("#new") :
                    DM = dateEnter();
                    ListOfTasks newList = new ListOfTasks(DM[0] + "/" + DM[1]);
                    TaskLists.addDay(newList);
                    TaskLists.showDays();
                    correct = true;
                    break;

                case ("#prev") :
                    TaskLists = FileCoder.uploadFromFile();
                    // TaskLists = FileCoder.deserializeFromBinF();
                    correct = true;
                    break;

                case ("#save") :
                    FileCoder.codeToFile(TaskLists);
                    correct = true;
                    break;
            }

            if (!correct)
            {
                System.out.println(" < INCORRECT COMMAND > the cmd should start with '#' symbol");
            }
        }
    }

    private static void instructions()
    {
        System.out.println(" \n< Available commands & their actions > ");
        System.out.println(" #lists - choose another date");
        System.out.println(" #write - create & add task");
        System.out.println(" #read - show all tasks on this day");
        System.out.println(" #task - edit exact task");
        System.out.println(" #new - create date list");
        System.out.println(" #prev - upload previous session");
        System.out.println(" #save - save current state for last session\n");
    }

    private static void editingCommands()
    {
        System.out.println("< Task is successfully extracted >\nChoose what do you want to edit :");
        System.out.println(" \t\t> To delete this task, enter - 0");
        System.out.println(" \t\t> To edit it's time, enter - 1");
        System.out.println(" \t\t> To change it's notes, enter - 2");
        System.out.println(" \t\t> To mark task as completed, enter - 3");
    }

    private static int[] timeEnter()
    {
        System.out.println("}*- Enter time when event / task starts :");
        System.out.println("\t| Format is [int hour], [int minutes] |\n > ");
        Integer hour = console.nextInt();
        console.nextLine();

        System.out.print(hour.toString() + " : ");
        Integer minutes = console.nextInt();
        console.nextLine();

        int[] arr = {hour, minutes};
        if (hour > 23 && minutes > 59) {
            System.out.println("\n< ERROR > Incorrect time format.");
            return null;
        }
        return arr;
    }

    private static int[] dateEnter()
    {
        System.out.println("}*- Enter the date you want to write down plans at :");
        System.out.print("\t| Format is [int day], [int month] |\n > day - ");
        int day = console.nextInt();
        console.nextLine();
        System.out.print(" , month - ");
        int mon = console.nextInt();
        console.nextLine();

        int[] arr = {day, mon};
        return arr;
    }
}