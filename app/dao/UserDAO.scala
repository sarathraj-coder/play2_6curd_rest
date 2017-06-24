package dao

import java.util.Date

import models.{PageData, Role, User, UsersTable}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}

import scala.concurrent.{ExecutionContext, Future}
import javax.inject.Inject

import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import java.sql.{Date => SqlDate}

import play.db.NamedDatabase
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.Future
/**
  * Created by sarathraj on 22/06/17.
  */


class UserDAO @Inject() (protected val dbConfigProvider: DatabaseConfigProvider)
                        (implicit executionContext: ExecutionContext)
  extends HasDatabaseConfigProvider[JdbcProfile]  {
  //import profile.api._


  //private val UserTable = TableQuery[Users]
  val UserTable = TableQuery[UsersTable]


   def all(): Future[Seq[User]] = db.run(UserTable.result)


   def insert(user: User): Future[Long] = {
     val action = (UserTable returning UserTable.map(_.id)) += user
     db.run(action)
   }


   def update(id: Long, user: User): Future[Int] = {
     val q = for { c <- UserTable if c.id === id } yield c
     val updateAction = q.update(user)
     db.run(updateAction)
   }

   def delete(id: Long): Future[Int] = {
     val q = UserTable.filter(_.id === id)
     val action = q.delete
     db.run(action)
   }

   def list(page: Int = 0, pageSize: Int = 10, orderBy: Int = 1, filter: String = "%"): Future[PageData] = {
     try {
       val offset = pageSize * page
       val query =
         (for {
           user <- UserTable if user.name.toLowerCase like filter.toLowerCase
         } yield (user)).drop(offset).take(pageSize)
       val totalRows = count(filter)
       val result = db.run(query.result)
       result flatMap (employees => totalRows map (rows => PageData(employees, page, offset, rows)))
     } finally { db.close() }
   }

   def findById(id: Long): Future[User] = {
     db.run(UserTable.filter(_.id===id).result.head)
   }

   def count(filter:String): Future[Int] = {
     db.run(UserTable.filter(_.name.toLowerCase like filter.toLowerCase()).length.result)
   }

   def count: Future[Int] =
     db.run(UserTable.length.result)



}





