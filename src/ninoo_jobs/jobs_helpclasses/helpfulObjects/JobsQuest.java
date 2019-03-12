package ninoo_jobs.jobs_helpclasses.helpfulObjects;

import java.util.List;

public class JobsQuest {
    public String questname;
    public String jobname;
    public List<JobsRItem> list;
    public JobsQuest neededQuests;

    public JobsQuest(String questname, String jobname, List<JobsRItem> list, JobsQuest neededQuests) {
        this.questname = questname;
        this.jobname = jobname;
        this.list = list;
        this.neededQuests = neededQuests;
    }

}

