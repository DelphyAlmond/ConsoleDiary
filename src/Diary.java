public class Diary {
    private ListOfTasks[] days;
    private int size = 0;

    public Diary() // no default [*]
    {
        // days = new ListOfTasks[7];
        // size = 7;
    }

    public int getSize()
    {
        return size;
    }

    public ListOfTasks getDay(int i)
    {
        return days[i - 1];
    }

    public void addDay(ListOfTasks listObj)
    {
        size++;
        ListOfTasks[] listsNew = new ListOfTasks[size];

        if (days.length > 0) {
            int i = 0;
            for (ListOfTasks d : days) {
                listsNew[i] = d;
                i++;
            }
        }
        listsNew[size - 1] = listObj;
        days = listsNew;
    }

    public void showDays()
    {
        System.out.println(" < DATE LISTS (2024) > ");
        Integer k = 0;
        for (ListOfTasks day : days)
        {
            k++;
            System.out.println(k.toString() + ") " + day.dateKey());
        }
    }
}