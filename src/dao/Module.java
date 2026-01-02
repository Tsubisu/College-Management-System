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
                model.Module module = new model.Module(moduleId,courseId,moduleName,courseYear,courseSemester,moduleDuration);
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


    public ArrayList<model.Module> getModulesByDepartment(int departmentId)
    {       ArrayList<model.Module> modules= new ArrayList<>();
        Connection conn = mySql.openConnection();
        String sql = "CALL getModulesByDepartmentId(?)";
        try(CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1,departmentId);
            ResultSet result=mySql.runQuery(conn, cs);
            while(result.next())
            {
                int moduleId= result.getInt(1);
                String moduleName= result.getString(2);
                int moduleDuration = result.getInt(3);
                int courseYear = result.getInt(4);
                int courseSemester=  result.getInt(5);
                int courseId=result.getInt("courseId");
                String courseName= result.getString("courseName");
                model.Module module = new model.Module(moduleId,courseId,moduleName,courseName,courseYear,courseSemester,moduleDuration);
                modules.add(module);
            }



        }
        catch(SQLException e){
            System.out.print(e);
        }
        finally {
            mySql.closeConnection(conn);
        }
        return modules;
    }

    public boolean addNewTeacherModules(model.Teacher teacher) {
        Connection conn = mySql.openConnection();
        boolean success = true;

        try {
            String sql = "CALL AddTeacherModule(?, ?)";
            for (model.Module module : teacher.getNewTeacherModulesToAdd()) {
                try (CallableStatement cs = conn.prepareCall(sql)) {
                    cs.setInt(1, teacher.getTeacherId());
                    cs.setInt(2, module.getModuleId());
                    mySql.executeUpdate(conn,cs);
                } catch (Exception e) {
                    System.out.println("Error adding module " + module.getModuleId() + ": " + e);
                    success = false;
                }
            }
        } finally {
            mySql.closeConnection(conn);
        }

        return success;
    }



    public boolean deleteTeacherModules(model.Teacher teacher) {
        Connection conn = mySql.openConnection();
        boolean success = true;

        try {
            String sql = "CALL DeleteTeacherModule(?, ?)";
            for (model.Module module : teacher.getTeacherModulesToDelete()) {
                try (CallableStatement cs = conn.prepareCall(sql)) {
                    cs.setInt(1, teacher.getTeacherId());
                    cs.setInt(2, module.getModuleId());
                    cs.execute();
                } catch (Exception e) {
                    System.out.println("Error deleting module " + module.getModuleId() + ": " + e);
                    success = false;
                }
            }
        } finally {
            mySql.closeConnection(conn);
        }

        return success;
    }

    public ArrayList<model.Module> getBatchModules(model.Batch batch)
    {
        ArrayList<model.Module> modules= new ArrayList<>();
        Connection conn = mySql.openConnection();
        String sql = "CALL GetBatchModules(?)";
        try(CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1,batch.getBatchId());
            ResultSet result=mySql.runQuery(conn, cs);
            while(result.next())
            {
                int moduleId= result.getInt(1);
                String moduleName= result.getString(2);
                int moduleDuration = result.getInt(3);
                int courseYear = result.getInt(4);
                int courseSemester=  result.getInt(5);
//
                model.Module module = new model.Module(moduleId,moduleName,courseYear,courseSemester,moduleDuration);
                modules.add(module);
            }



        }
        catch(SQLException e){
            System.out.print(e);
        }
        finally {
            mySql.closeConnection(conn);
        }
        return modules;

    }

    public ArrayList<model.Teacher> getTeachersByModule(int moduleId) {
        ArrayList<model.Teacher> teachers = new ArrayList<>();
        Connection conn = mySql.openConnection();
        String sql = "{CALL GetTeachersByModule(?)}";
        try (CallableStatement cs = conn.prepareCall(sql)) {
            cs.setInt(1, moduleId);
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                int teacherId = rs.getInt("teacherId");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                String departmentName = rs.getString("departmentName");

                model.Teacher teacher = new model.Teacher(teacherId, firstName, lastName, departmentName);
                teachers.add(teacher);
            }

            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            mySql.closeConnection(conn);
        }

        return teachers;
    }














}
