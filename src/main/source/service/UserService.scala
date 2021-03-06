package service

import java.sql.Connection

import bean.ResultMessage
import dao.{DaoFactory, UserDao}
import model.User


/**
  * Created by ReggieYang on 2016/11/6.
  */
class UserService(conn: Connection) {

  val userDao = new UserDao(conn)

  def addUser(user: Array[String]) = {
    val newUserName = user.head
//    val isUserExist = userDao.getUser(newUserName) == null
    DaoFactory.save(conn, userDao.userTable, user, userDao.userColumns)
  }

  def verifyUser(userName: String, password: String): ResultMessage = {
    lazy val SUCCESS = "success"
    lazy val FAIL = "fail"
    lazy val NO_SUCH_USER = "no such user"
    lazy val PASSWORD_WRONG = "password wrong"

    val user = userDao.getUser(userName)
    if (user == null) {
      ResultMessage(FAIL, NO_SUCH_USER)
    }
    else if (user.password == password) bean.ResultMessage(SUCCESS, "")
    else ResultMessage(FAIL, PASSWORD_WRONG)

  }

  def getPosition(userName: String): String = userDao.getUser(userName).position

  def getColleague(company: String): Array[User] = {
    userDao.getColleague(company)
  }

}
