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

    public TaskLine(String task, String additionalInf, String timeStr,
                    String createdT)
    {
        crTime = createdT;
        strTask = task;
        taskNotes = additionalInf;
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
        int lenOfTaskStr = strTask.length();
        int lenOfTaskNotes = taskNotes.length();

        int leftStrChars = lenOfTaskNotes % lenOfTaskStr;

        String wholeInf = (" < " + time + " > " + "\n" + (Integer)currCode).toString() + ". "
                + strTask + " " + (done ? "[ V ]" : "[ 0 ]") + "|" + crTime + "\n" +
                (edTime == null ? "(|wasn't edited yet)" : "|" + edTime);

        int s = 0;
        for (int steps = 1; steps <= lenOfTaskNotes / lenOfTaskStr; steps++)
        {
            wholeInf += "\t" + taskNotes.substring(s, lenOfTaskStr * steps) + "\n";
            s += lenOfTaskStr;
        }

        wholeInf += "\t" + taskNotes.substring(s, s + leftStrChars) + "\n";
        return wholeInf;
    }
}
