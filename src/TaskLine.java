public class TaskLine {

    private String strTask;
    private boolean done = false;
    private String taskNotes;
    private String time;
    private String crTime;
    private String edTime;

    private int currCode;

    public void setDoneToTrue()
    {
        done = true;
    }

    public boolean getCondition()
    {
        return done;
    }

    public void setCode(int i)
    {
        currCode = i;
    }

    public int getCode()
    {
        return currCode;
    }

    public TaskLine(String task, String timeStr,
                    String createdT)
    {
        crTime = createdT;
        strTask = task;
        time = timeStr;
    }

    public void editTime(String t)
    {
        time = t;
    }

    public void setNewNotes(String txt)
    {
        taskNotes = txt;
    }

    public void setEditTime(String et)
    {
        edTime = et;
    }

    public String convertToLine(int next)
    {
        currCode = next;

        String wholeInf = ("\n < " + time + " > " + "\n" + (Integer)currCode).toString() + ". "
                + strTask + " " + (done ? "[ V ]" : "[ 0 ]") + " |" + crTime.substring(0, 5)
                + "\n" + (edTime == null ? "(|wasn't edited yet)" :
                "|" + edTime.substring(0, 5));

        if (taskNotes != null) {
            int lenOfTaskStr = strTask.length();
            int lenOfTaskNotes = taskNotes.length();
            int leftStrChars = lenOfTaskNotes % lenOfTaskStr;

            int s = 0;
            for (int steps = 1; steps <= lenOfTaskNotes / lenOfTaskStr; steps++) {
                wholeInf += "\n" + taskNotes.substring(s, lenOfTaskStr * steps);
                s += lenOfTaskStr;
            }
            wholeInf += taskNotes.substring(s, s + leftStrChars) + "\n";
        }
        return wholeInf;
    }

    public String writeToFileFormat()
    {
        String fileTaskLine = (((Integer)currCode).toString() + "\n" + time + "\n"
                + strTask + "\n" + taskNotes + "\n" + (done ? "1" : "0") +
                "\n" + crTime.substring(0, 5) + "\n" +
                (edTime == null ? "-" : edTime.substring(0, 5) + "\n"));

        // task code (place)  n
        // task time          h:m
        // task name          txt
        // notes              txt       => 7 lines
        // mark (0\V)         0 / 1
        // creation time      local t
        // edited time        local t

        return fileTaskLine;
    }
}
