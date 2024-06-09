import java.time.LocalTime;
import java.util.Scanner;

public class Main {

    static boolean flag = true;
    static Scanner console = new Scanner(System.in);

    public static void main(String[] args) {

        Diary TaskLists = new Diary();
        boolean correct;

        System.out.println("}*- Diary is empty by default, first of all add (create) new taskList.");
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
        int h = 1, m = 1;
        boolean flag = true;
        int[] arr = null;

        while (flag) {
            boolean hours = false, minutes = false;

            System.out.println("}*- Enter time when event / task starts :");
            System.out.println("\t| Format is [int hour] | > ");
            try {
                h = Integer.parseInt(console.nextLine());
            } catch (NumberFormatException e) {
                hours = true;
            }

            System.out.print(h + "\t| [int minutes] | > : ");
            try {
                m = Integer.parseInt(console.nextLine());
            } catch (NumberFormatException e) {
                minutes = true;
            }

            if (!hours && !minutes) {
                if (!(h > 23 && m > 59)) {
                    arr = new int[]{h, m};
                    flag = false;
                }
                else System.out.println("\n< ERROR > Incorrect values for time, try again : ");
            }
            else System.out.println("\n< ERROR > Incorrect time format, try again : ");
        }
        return arr;
    }

    private static int[] dateEnter()
    {
        int d = 1, m = 1;
        boolean flag = true;
        int[] arr = null;

        while (flag) {
            boolean day = false, month = false;

            System.out.println("}*- Enter the date you want to write down plans at :");
            System.out.print("\t| Format is [int day] | > day - ");
            try {
                d = Integer.parseInt(console.nextLine());
            } catch (NumberFormatException e) {
                day = true;
            }

            System.out.print("\t| [int month] | >       month - ");
            try {
                m = Integer.parseInt(console.nextLine());
            } catch (NumberFormatException e) {
                month = true;
            }

            if (!day && !month) {
                if (!(d > 31 || m > 12 || m == 0 || d == 0)) {
                    arr = new int[]{d, m};
                    flag = false;
                }
                else System.out.println("\n< ERROR > Incorrect date values, try again : ");
            }
            else System.out.println("\n< ERROR > Incorrect date format, try again : ");
        }
        return arr;
    }
}