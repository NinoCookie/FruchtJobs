package ninoo_jobs.jobs_db;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;

import java.sql.*;
import java.time.Instant;
import java.util.*;

public class JobsDBManager {
    Connection connection;
    Statement statement;
    ResultSet rs;
    Plugin plugin;

    public JobsDBManager() {

    }

    public void mySQLConnection(){
        try {
            String url = "jdbc:mysql://" + plugin.getConfig().getString("Database.host") + ":" + plugin.getConfig().getString("Database.port") + "/" + plugin.getConfig().getString("Database.name");
            connection=DriverManager.getConnection(url, plugin.getConfig().getString("Database.username"), plugin.getConfig().getString("Database.password"));
        }catch (SQLException exc){
            exc.printStackTrace();
        }
    }

    public boolean createtable(){
        try {
            String sql = "create table if not exists jobsplayers(id int NOT NULL AUTO_INCREMENT primary key, uuid varchar(36), job varchar(36), xp double, lvl int)";
            statement = connection.createStatement();
            statement.executeUpdate(sql);
            return true;
        }catch (SQLException exc){
            exc.printStackTrace();
        }
        return false;
    }

    public boolean hasjob(String uuidx, String job){
        try {
            String sql="SELECT * FROM jobsplayers WHERE uuid='" + uuidx + "' AND job='" + job + "'";
            statement=connection.createStatement();
            statement.executeQuery(sql);
            rs=statement.getResultSet();
            if(rs.next()){
                if(rs.getString(2)!=null){
                    return true;
                }
            }
        }catch (SQLException exc){
            exc.printStackTrace();
        }
        return false;
    }
    public boolean remove_playerjob(String uuidx, String job){
        try {
            String sql = "DELETE FROM jobsplayers WHERE uuid='" + uuidx + "' AND job='" + job + "'";
            statement=connection.createStatement();
            statement.executeUpdate(sql);
            return true;
        }catch (SQLException exc){
            exc.printStackTrace();
        }
        return false;
    }

    public boolean register_playerjob(String uuidx, String job){
        try {
            String sql = "INSERT INTO jobsplayers(uuid,job,xp,lvl) VALUES ('" + uuidx + "','"+ job +"',0.0,0)";
            statement = connection.createStatement();
            statement.executeUpdate(sql);
            return true;
        }catch (SQLException exc){
            exc.printStackTrace();
        }
        return false;
    }

    public boolean contains(String uuidx) throws SQLException {
        statement = connection.createStatement();
        rs = statement.executeQuery("select * from jobsplayers where uuid='" + uuidx + "'");
        if (rs.next()) {
            return rs.getInt(2) >= 1;
        }
        return false;
    }

    public void insertxp(String uuidx, String job, double xpx) {
        try {
            statement = connection.createStatement();
            String sql = "select * from jobsplayers WHERE uuid='" + uuidx + "' AND job='" + job + "'";
            rs = statement.executeQuery(sql);
            if (rs.next()) {
                xpx = xpx + rs.getDouble(4);
                xpx=xpx*plugin.getConfig().getDouble("Events.multiplier");
                sql = "update jobsplayers set xp ='" + xpx + "' WHERE uuid='" + uuidx + "' AND job='" + job + "'";
                statement = connection.createStatement();
                statement.executeUpdate(sql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removexp(String uuidx, String job, double xpx, boolean full) {
        try {
            statement = connection.createStatement();
            String sql = "select * from jobsplayers WHERE uuid='" + uuidx + "' AND job='" + job + "'";
            rs = statement.executeQuery(sql);
            if (rs.next()) {
                if (xpx > rs.getDouble(4) || full) {
                    sql = "update jobsplayers set xp = 0 WHERE uuid='" + uuidx + "' AND job='" + job + "'";
                } else {
                    double am=rs.getDouble(4)-xpx;
                    sql = "update jobsplayers set xp ='" + am + "' WHERE uuid='" + uuidx + "' AND job='" + job + "'";
                }
                statement = connection.createStatement();
                statement.executeUpdate(sql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void insertlvl(String uuidx, String job, int amount) {
        try {
            statement = connection.createStatement();
            String sql = "select * from jobsplayers WHERE uuid='" + uuidx + "' AND job='" + job + "'";
            rs = statement.executeQuery(sql);
            if (rs.next()) {
                amount+=rs.getInt(5);
                sql = "update jobsplayers set lvl ='" + amount + "' WHERE uuid='" + uuidx + "' AND job='" + job + "'";
                statement = connection.createStatement();
                statement.executeUpdate(sql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removelvl(String uuidx, String job, int lvlx) {
        try {
            statement = connection.createStatement();
            String sql = "select * from jobsplayers WHERE uuid='" + uuidx + "' AND job='" + job + "'";
            rs = statement.executeQuery(sql);
            if (rs.next()) {
                if (lvlx > rs.getInt(5)) {
                    sql = "update jobsplayers set lvl = 0 WHERE uuid='" + uuidx + "' AND job='" + job + "'";
                } else {
                    int am=rs.getInt(5)-lvlx;
                    sql = "update jobsplayers set lvl ='" + am + "' WHERE uuid='" + uuidx + "' AND job='" + job + "'";
                }
                statement = connection.createStatement();
                statement.executeUpdate(sql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getlvl(String uuidx, String job){
        try {
            statement=connection.createStatement();
            String sql = "select * from jobsplayers WHERE uuid='" + uuidx + "' AND job='" + job + "'";
            rs = statement.executeQuery(sql);
            if(rs.next()){
                return rs.getInt(5);
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return 0;
    }

    public double getxp(String uuidx, String job){
        try {
            statement=connection.createStatement();
            String sql = "select * from jobsplayers WHERE uuid='" + uuidx + "' AND job='" + job + "'";
            rs = statement.executeQuery(sql);
            if(rs.next()){
                return rs.getDouble(4);
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return 0.0;
    }

    public String showstats(String uuidx, String job, String jobcolor){
        try {
            statement=connection.createStatement();
            String sql = "select * from jobsplayers WHERE uuid='" + uuidx + "' AND job='" + job + "'";
            rs=statement.executeQuery(sql);
            if(rs.next()){
               return ChatColor.valueOf(jobcolor)+rs.getString(3)+ChatColor.GOLD+" XP: "+ChatColor.valueOf(jobcolor)+ rs.getDouble(4)+ChatColor.GOLD+" Level: "+ChatColor.valueOf(jobcolor)+rs.getInt(5);
            }
        }catch (SQLException exc){
            exc.printStackTrace();
        }
        return "Glückwunsch wenn das passiert.";
    }

    public boolean register_location_table(){
        try {
            String sql = "create table if not exists jobslocations(id int NOT NULL AUTO_INCREMENT primary key,x int, y int, z int, world varchar(36), regdate timestamp)"; //testing
            statement = connection.createStatement();
            statement.executeUpdate(sql);
            return true;
        }catch (SQLException exc){
            exc.printStackTrace();
        }
        return false;
    }
    public void registerlocation(int x, int y, int z, String world){
        try{
            Timestamp timestamp=Timestamp.from(Instant.now());
            String sql = "INSERT INTO jobslocations(x,y,z,world,regdate) VALUES ('"+ x +"','"+ y +"','"+ z +"','"+ world +"','"+timestamp+"')"; //testing
            statement = connection.createStatement();
            statement.executeUpdate(sql);
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isLocationReg(int x,int y,int z,String world){
        try{
            String sql = "select * from jobslocations WHERE x='" + x + "' AND y='" + y + "' AND z='" + z + "' AND world='" + world + "'";
            statement=connection.createStatement();
            rs=statement.executeQuery(sql);
            if(rs.next()){
                if(x==rs.getInt(2) && y==rs.getInt(3) && z==rs.getInt(4) && world.equalsIgnoreCase(rs.getString(5))){
                    return true;
                }
                else{
                    return false;
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public void checkAndRemoveLocationsByTime(){
        try {
            String sql="select * from jobslocations";
            statement=connection.createStatement();
            ResultSet res=statement.executeQuery(sql);
            while(res.next()){
                int x=res.getInt(2);
                int y=res.getInt(3);
                int z=res.getInt(4);
                String world=res.getString(5);
                if(isLocationReg(x,y,z,world)){
                    Timestamp timenow=Timestamp.from(Instant.now());
                    Timestamp registeredtime=rs.getTimestamp(6);
                    String timer=plugin.getConfig().getString("Events.protectiontime"); //in seconds
                    if(timer.indexOf('s')>=0){
                        String[] s=timer.split("s");
                        double time=timenow.getTime()-registeredtime.getTime();
                        time=time*0.001;
                        if(time>=Integer.parseInt(s[0])){
                            sql = "delete FROM jobslocations WHERE x='" + x + "' AND y='" + y + "' AND z='" + z + "' AND world='" + world + "'";
                            statement = connection.createStatement();
                            statement.executeUpdate(sql);
                        }
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public boolean register_bountystats_table(){
        try {
            String sql = "create table if not exists bountystats(id int NOT NULL AUTO_INCREMENT primary key, uuid varchar(36), bountyname varchar(36), rarity varchar(36), counter int)";
            statement = connection.createStatement();
            statement.executeUpdate(sql);
            return true;
        }catch (SQLException exc){
            exc.printStackTrace();
        }
        return false;
    }

    public void registerbounty(String uuid, String bountyname, String rarity, boolean stats){
        try{
            if(stats){
                String sql = "select * from bountystats WHERE uuid='" + uuid + "' AND bountyname='" + bountyname + "' AND rarity='" + rarity + "'";
                statement = connection.createStatement();
                rs=statement.executeQuery(sql);
                if(rs.next()){
                    int counter=rs.getInt(5)+1;
                    sql = "update bountystats set counter ='" + counter + "' WHERE uuid='" + uuid + "' AND bountyname='" + bountyname + "' AND rarity='" + rarity + "'";
                    statement = connection.createStatement();
                    statement.executeUpdate(sql);
                }
                else{
                    sql = "INSERT INTO bountystats(uuid,bountyname,rarity,counter) VALUES ('" + uuid + "','"+ bountyname +"','"+ rarity +"',1)";
                    statement = connection.createStatement();
                    statement.executeUpdate(sql);
                }
                sql = "select * from bounties WHERE uuid='" + uuid + "' AND bountyname='" + bountyname + "' AND rarity='" + rarity + "'";
                statement = connection.createStatement();
                rs=statement.executeQuery(sql);
                if(rs.next()){
                    int counter=rs.getInt(5)+1;
                    sql = "update bounties set counter ='" + counter + "' WHERE uuid='" + uuid + "' AND bountyname='" + bountyname + "' AND rarity='" + rarity + "'";
                    statement = connection.createStatement();
                    statement.executeUpdate(sql);
                }
                else{
                    sql = "INSERT INTO bounties(uuid,bountyname,rarity,counter) VALUES ('" + uuid + "','"+ bountyname +"','"+ rarity +"',1)";
                    statement = connection.createStatement();
                    statement.executeUpdate(sql);
                }
            }
            else{
                String sql = "select * from bounties WHERE uuid='" + uuid + "' AND bountyname='" + bountyname + "' AND rarity='" + rarity + "'";
                statement = connection.createStatement();
                rs=statement.executeQuery(sql);
                if(rs.next()){
                    int counter=rs.getInt(5)+1;
                    sql = "update bounties set counter ='" + counter + "' WHERE uuid='" + uuid + "' AND bountyname='" + bountyname + "' AND rarity='" + rarity + "'";
                    statement = connection.createStatement();
                    statement.executeUpdate(sql);
                }
                else{
                    sql = "INSERT INTO bounties(uuid,bountyname,rarity,counter) VALUES ('" + uuid + "','"+ bountyname +"','"+ rarity +"',1)";
                    statement = connection.createStatement();
                    statement.executeUpdate(sql);
                }
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public boolean register_bounty_table(){
        try {
            String sql = "create table if not exists bounties(id int NOT NULL AUTO_INCREMENT primary key, uuid varchar(36), bountyname varchar(36), rarity varchar(36), counter int)";
            statement = connection.createStatement();
            statement.executeUpdate(sql);
            return true;
        }catch (SQLException exc){
            exc.printStackTrace();
        }
        return false;
    }

    public List<String> getEntries(String uuid){
        try{
            String sql = "select * from bounties where uuid='"+uuid+"'";
            statement=connection.createStatement();
            rs=statement.executeQuery(sql);
            List<String> entries=new ArrayList<>();
            while(rs.next()){
                entries.add(rs.getString(3)+"-"+rs.getString(4)+"-"+rs.getInt(5));
            }
            return entries;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public void removeBountyEntry(String uuid, String bountyname, String rarity){
        try {
            statement = connection.createStatement();
            String sql = "select * from bounties WHERE uuid='" + uuid + "' AND bountyname='" + bountyname + "' AND rarity='" +rarity+ "'";
            rs = statement.executeQuery(sql);
            if (rs.next()) {
                if (rs.getInt(5)<=1) {
                    sql = "delete from bounties WHERE uuid='" + uuid + "' AND bountyname='" + bountyname + "' AND rarity='" +rarity+ "'";
                } else {
                    int am=rs.getInt(5)-1;
                    sql = "update bounties set counter ='" + am + "' WHERE uuid='" + uuid + "' AND bountyname='" + bountyname + "' AND rarity='" +rarity+ "'";
                }
                statement = connection.createStatement();
                statement.executeUpdate(sql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<String> getTopOfJob(String job, int amount){
        HashMap<String, Integer> playermap=new HashMap<>();
        try {
            statement=connection.createStatement();
            String sql="select * from jobsplayers where job="+job;
            rs=statement.executeQuery(sql);
            while(rs.next()){
                if(rs.getString(2).equalsIgnoreCase(job)){
                    playermap.put(rs.getString(1), rs.getInt(4));
                }
            }
            return sort(playermap);
        }catch (Exception e){

        }
        return null;
    }
    public List<String> sort(HashMap<String,Integer> map){
        Map<String, Integer> sorted=sortByValues(map);
        if(sorted!=null){
            ArrayList<String> sortedList=new ArrayList<>();
            Set set2 = sorted.entrySet();
            Iterator iterator2 = set2.iterator();
            while ((iterator2.hasNext())){
                Map.Entry me2=(Map.Entry)iterator2.next();
                UUID uuid=UUID.fromString(me2.getKey().toString());
                sortedList.add(Bukkit.getPlayer(uuid).getDisplayName()+" "+me2.getValue().toString());
            }
            return sortedList;
        }
        return null;
    }
    private static HashMap sortByValues(HashMap map) {
        List list = new LinkedList(map.entrySet());
        // Defined Custom Comparator here
        Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Comparable) ((Map.Entry) (o1)).getValue())
                        .compareTo(((Map.Entry) (o2)).getValue());
            }
        });
        return null;
    }







    public void close(){
        try {
            if (rs != null) {
                rs.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public Statement getStatement() {
        return statement;
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public void setPlugin(Plugin plugin) {
        this.plugin = plugin;
    }
}

