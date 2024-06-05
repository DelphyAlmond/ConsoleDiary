public class ListOfTasks {

    private String date;
    private TaskLine[] tasks;
    private int size;

    public String dateKey()
    {
        return date;
    }

    public int getSize()
    {
        return size;
    }

    public TaskLine getTaskAt(int indx)
    {
        return tasks[indx - 1];
    }

    public ListOfTasks(String monthDay)
    {
        date = monthDay;
        size = 0;
    }

    public void setUpdatedtask(TaskLine tsk, int place)
    {
        tasks[place - 1] = tsk;
    }

    public void addTask(TaskLine tsk) // adds task, resizing the list
    {
        size++;
        TaskLine[] tasksNew = new TaskLine[size];
        if (tasks.length > 0) {
            int i = 0;
            for (TaskLine t : tasks) {
                tasksNew[i] = t;
                tasksNew[i].setCode(i + 1);
                i++;
            }
        }
        tasksNew[size - 1] = tsk;
        tasks = tasksNew;
    }

    public void deleteTaskAt(int plc)
    {
        TaskLine[] tasksNew = new TaskLine[--size];
        int i = 0;
        if (tasks.length > 0) {
            for (TaskLine t : tasks) {
                if (i != plc - 1) {
                    tasksNew[i] = t;
                    tasksNew[i].setCode(i + 1);
                    i++;
                }
            }
        }
        tasks = tasksNew;
    }

    public void showList()
    {
        TaskLine[] doneTasks = new TaskLine[size];

        int i = 0, k = 0;
        for (TaskLine t : tasks)
        {
            i++;
            if (!t.getCondition())
            {
                System.out.print(t.convertToLine(i));
            }
            else
            {
                doneTasks[k] = t;
                k++;
            }
        }

        System.out.println("< COMPLETED > -----------------------------------------------------");

        for (TaskLine t : doneTasks)
        {
            if (i <= size) {
                System.out.print(t.convertToLine(i));
                i++;
            }
        }
    }
}
