import java.time.LocalTime;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        boolean flag = true;
        Scanner console = new Scanner(System.in);

        Diary TaskLists = new Diary();

        System.out.println("}*- Diary is empty rn, first of all add (create) new List.");
        System.out.println("\t| Format is [int day], [int month] |");
        System.out.print(" > Enter the date you want to write down plans at : day - ");
        String day = console.nextLine();
        System.out.print(" , month - ");
        String mon = console.nextLine();

        ListOfTasks currList = new ListOfTasks(day + "/" + mon);
        TaskLists.addDay(currList);

        while (flag) {

            instructions();
            // String choiceStr = console.nextLine();
            // for future commands
            int choiceInt = console.nextInt();

            switch (choiceInt) {

                case (0) :
                    // register & add exact task
                    System.out.println("}*- Enter time when event / task starts :");
                    System.out.println("\t| Format is [int hour], [int minutes] |");
                    System.out.print("\n > ");
                    Integer hour = console.nextInt();
                    System.out.print(" : ");
                    Integer minutes = console.nextInt();

                    if (hour > 23 && minutes > 59) {
                        System.out.println("\n< ERROR > Incorrect time format.");
                        break;
                    } else {
                        System.out.println("}*- Write a task that has to be done :");
                        System.out.println("\t| Format is [string text] |");
                        System.out.print("\n > ");
                        String task = console.nextLine();

                        System.out.println("}*- If you have any extra information, enter some notes :");
                        System.out.println("\t| Format is [string text] |");
                        System.out.print("\n > ");
                        String note = (console.nextLine() == "\n") ? null : console.nextLine();

                        String time = hour.toString() + ":" + minutes.toString();
                        LocalTime currCreatingTime = LocalTime.now();

                        TaskLine taskObj = new TaskLine(task, note, time, currCreatingTime.toString());
                        // add task to the current day list
                        currList.addTask(taskObj);
                    }
                    break;

                case (1) :
                    // show all added tasks to current list
                    currList.showList();
                    break;

                case(2) :
                    // redact exact task
                    System.out.println("}*- Enter the index of event / task you would like to edit :");
                    System.out.println("\t| Format is [int place] |");
                    int place = console.nextInt();

                    if (place > currList.getSize())
                    {
                        break;
                    }
                    else {
                        TaskLine tskObj = currList.getTaskAt(place);

                        System.out.println("< Task is successfully extracted >");

                        System.out.println(" \t\t> To delete this task, enter 'r' / 'remove'");
                        System.out.println(" \t\t> To edit it's time, enter 't' / 'time'");
                        System.out.println(" \t\t> To change it's notes, enter 'n' / 'notes'");
                        System.out.println(" \t\t> To mark task as completed, enter 'd' / 'done'");

                        // future EDITING [!!!]

                        LocalTime currEditingTime = LocalTime.now();
                        tskObj.setEdit(currEditingTime.toString());
                    }
                    break;

                case (3) :
                    TaskLists.showDays();

                    // moving from one date to another
                    System.out.println("}*- Enter the index of date you would like to edit & switch to :");
                    System.out.println("\t| Format is [int place] |");

                    int number = console.nextInt();
                    if (number <= TaskLists.getSize()) {
                        currList = TaskLists.getDay(number);
                    }
                    break;

                case (4) :
                    System.out.println("}*- Enter the date you want to write down plans at : day - ");
                    day = console.nextLine();
                    System.out.print(" , month - ");
                    mon = console.nextLine();
                    ListOfTasks newList = new ListOfTasks(day + "/" + mon);
                    TaskLists.addDay(newList);
                    TaskLists.showDays();
                    break;
            }
        }
    }

    private static void instructions()
    {
        System.out.println(" < Available commands & their actions > ");
        System.out.println(" [0] - create & add task");
        System.out.println(" [1] - show all tasks on this day");
        System.out.println(" [2] - edit exact task"); // [!!!] fin
        System.out.println(" [3] - choose another date");
        System.out.println(" [4] - create date list");
    }
}