public class ListOfTasks {

    private String date;
    private TaskLine[] tasks;
    private int size;
    private String forFileWr;

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
        int i = 0;
        for (TaskLine tsk : tasks)
        {
            if (tsk.getCode() == indx)
            {
                return tasks[i];
            }
            i++;
        }
        return null;
    }

    public ListOfTasks(String monthDay)
    {
        date = monthDay;
        size = 0;
    }

    public String getFileFormat()
    {
        return forFileWr;
    }

    public void setUpdatedTask(TaskLine tsk, int place)
    {
        tasks[place - 1] = tsk;
    }

    public void addTask(TaskLine tsk) // adds task, resizing the list
    {
        size++;
        TaskLine[] tasksNew = new TaskLine[size];
        if (tasks != null && tasks.length > 0) {
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
        forFileWr = date + "/24\n";
        TaskLine[] doneTasks = new TaskLine[size];
        System.out.print(" -<* " + date + "/2024 *>- ");

        if (tasks != null) {
            TaskLine[] newTasks = new TaskLine[size];
            int i = 0, k = 0;
            for (TaskLine t : tasks) {
                if (!t.getCondition()) {
                    i++;
                    t.setCode(i);
                    System.out.print(t.convertToLine());
                    // after code(index)* was set :
                    forFileWr += t.writeToFileFormat();
                    newTasks[i - 1] = t;
                } else {
                    doneTasks[k] = t;
                    k++;
                }
            }

            if (doneTasks[0] != null) {
                System.out.println("\n< COMPLETED > ----------------------------------------------");
                for (TaskLine t : doneTasks) {
                    if (i <= size && t != null) {
                        i++;
                        System.out.print(t.convertToLine());
                        forFileWr += t.writeToFileFormat();
                        newTasks[i - 1] = t;
                    }
                }
            }

            tasks = newTasks;
        }
        else System.out.println("\n< LIST IS EMPTY RN > Write down some tasks.");
    }
}
