package dao;

import database.MySqlConnection;
import model.Chapter;

import java.sql.*;
import java.util.ArrayList;


public class Module {
    MySqlConnection mySql = MySqlConnection.getMySqlConnection();


    public ArrayList<model.Module> getAllStudentModules(int studentId)
    {       ArrayList<model.Module> allStudentModule= new ArrayList<>();
            Connection conn = mySql.openConnection();
            String sql = "CALL GetStudentModules(?)";
            try(CallableStatement cs = conn.prepareCall(sql)) {

                cs.setInt(1,studentId);
                ResultSet result=mySql.runQuery(conn, cs);
                while(result.next())
                {
                    int moduleId= result.getInt(1);
                    String moduleName= result.getString(2);
                    int moduleDuration = result.getInt(3);
                    int courseYear = result.getInt(4);
                    int courseSemester=  result.getInt(5);
                    model.Module module = new model.Module(moduleId,0,moduleName,courseYear,courseSemester,moduleDuration);
                    allStudentModule.add(module);
                }



            }
            catch(SQLException e){
                System.out.print(e);
            }
            finally {
                mySql.closeConnection(conn);
            }
    return allStudentModule;
    }


    public ArrayList<model.Module> getAllTeacherModules(int teacherId)
    {       ArrayList<model.Module> allTeacherModule= new ArrayList<>();
        Connection conn = mySql.openConnection();
        String sql = "CALL GetTeacherModules(?)";
        try(CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1,teacherId);
            ResultSet result=mySql.runQuery(conn, cs);
            while(result.next())
            {
                int moduleId= result.getInt(1);
                String moduleName= result.getString(2);
                int moduleDuration = result.getInt(3);
                int courseYear = result.getInt(4);
                int courseSemester=  result.getInt(5);
                model.Module module = new model.Module(moduleId,0,moduleName,courseYear,courseSemester,moduleDuration);
                allTeacherModule.add(module);
            }



        }
        catch(SQLException e){
            System.out.print(e);
        }
        finally {
            mySql.closeConnection(conn);
        }
        return allTeacherModule;
    }

    public ArrayList<model.Chapter> getChaptersByModule(int moduleId)
    {
        ArrayList<model.Chapter> allChapters= new ArrayList<>();
        Connection conn = mySql.openConnection();
        String sql = "CALL GetChaptersByModule(?)";
        try(CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1,moduleId);
            ResultSet result=mySql.runQuery(conn, cs);
            while(result.next())
            {
                int chapterId= result.getInt("chapterId");
                String chapterName= result.getString("chapterName");
                String pdfPath=result.getString("pdfPath");
                allChapters.add(new Chapter(chapterId,chapterName,pdfPath));
            }



        }
        catch(SQLException e){
            System.out.print(e);
        }
        finally {
            mySql.closeConnection(conn);
        }
        return allChapters;

    }

    public ArrayList<model.Module> getModulesByCourse(int courseId)
    {       ArrayList<model.Module> allStudentModule= new ArrayList<>();
        Connection conn = mySql.openConnection();
        String sql = "CALL GetModulesByCourse(?)";
        try(CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1,courseId);
            ResultSet result=mySql.runQuery(conn, cs);
            while(result.next())
            {
                int moduleId= result.getInt(1);
                String moduleName= result.getString(2);
                int moduleDuration = result.getInt(3);
                int courseYear = result.getInt(4);
                int courseSemester=  result.getInt(5);
                model.Module module = new model.Module(moduleId,0,moduleName,courseYear,courseSemester,moduleDuration);
                allStudentModule.add(module);
            }



        }
        catch(SQLException e){
            System.out.print(e);
        }
        finally {
            mySql.closeConnection(conn);
        }
        return allStudentModule;
    }

    public boolean addModule(model.Module module) {
        Connection conn= mySql.openConnection();
        String sql = "{CALL AddModule(?, ?, ?, ?, ?)}";
        try (CallableStatement stmt = conn.prepareCall(sql)) {

            // Set input parameters from Module object
            stmt.setInt(1, module.getCourseId());
            stmt.setString(2, module.getModuleName());
            stmt.setInt(3, module.getModuleDuration());
            stmt.setInt(4, module.getCourseYear());
            stmt.setInt(5, module.getCourseSemester());

            // Execute procedure
           mySql.runQuery(conn,stmt);
            return true;

        } catch (SQLException e) {
            System.out.print(e);
        }
        finally
        {
            mySql.closeConnection(conn);
        }
        return false;
    }

    public boolean deleteModuleById(int moduleId) {
        Connection conn= mySql.openConnection();
        String sql = "{CALL DeleteModuleById(?)}";

        try (CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setInt(1, moduleId);
            stmt.execute();
            return true;

        } catch (SQLException ex) {


            if ("45000".equals(ex.getSQLState())) {
                System.out.println("Custom DB error: " + ex.getMessage());
            } else {
                ex.printStackTrace();
            }

            return false;
        }
        finally
        {
            mySql.closeConnection(conn);
        }
    }




}
