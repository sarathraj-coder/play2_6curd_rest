//package dao
//
//import javax.inject.Inject
//
//import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
//import slick.jdbc.JdbcProfile
//import slick.lifted.TableQuery
//import models.{Role, User}
//import scala.concurrent.ExecutionContext
//
///**
//  * Created by sarathraj on 22/06/17.
//  */
//
//class RolesDAO @Inject() (protected val dbConfigProvider: DatabaseConfigProvider)
//                        (implicit executionContext: ExecutionContext)
//  extends HasDatabaseConfigProvider[JdbcProfile] {
//
//  import profile.api._
//
//  private val users = TableQuery[Roles]
//
//
//
//  class Roles(tag:Tag) extends  Table[Role](tag,"ROLE"){
//
//    val users = TableQuery[]
//
//    def id=column[Long]("ID",O.PrimaryKey, O.AutoInc)
//    def name = column[String]("NAME")
//    def idUser=column[Long]("ID_USER",O.PrimaryKey, O.AutoInc)
//    def * = (id.?, name, idUser) <> (Role.tupled, Role.unapply _)
//
//    // A reified foreign key relation that can be navigated to create a join
//    def user = foreignKey("USER_FK", idUser, users)(_.id)
//
//  }
//
//}