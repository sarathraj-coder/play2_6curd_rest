package controllers

import javax.inject._

import dao.UserDAO
import models.{Bad, User}
import play.api.mvc._
import play.api.libs.json.{JsError, JsSuccess, Json, _}
import play.api.Mode
import system.MyExecutionContext

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration
import scala.concurrent.Future


/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(cc: ControllerComponents,userDAO:UserDAO
                             )
  extends AbstractController(cc) {

  //   myExecutionContext: MyExecutionContext

  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }
implicit val userformat = Json.format[User]


  def create = Action.async(parse.json) { implicit request =>
    val validationResult: JsResult[User] = request.body.validate[User]
      validationResult match {
        case r: JsSuccess[User] =>
          val user:User=r.get
         val futureLong : Future[Long] =  userDAO.insert(user)
          futureLong.map{
            result => Ok("success")
          }
        case r: JsError =>
          scala.concurrent.Future {
            val errors = JsError.toJson(r)
            Ok(Json.toJson(Bad(Some(430), "bad.request", data = errors)))
          }
      }
  }


  def getAll = Action.async { implicit rs =>
    val users : Future[Seq[User]] =  userDAO.all()
    users.map(u => Ok(Json.toJson(u)))
  }



  def deleteUser(id:Long) = Action.async {  implicit request =>
      userDAO.delete(id).map(num => Ok(Json.toJson(num)))
  }


  def getById(id:Long) =Action.async{  implicit request =>
    userDAO.findById(id).map(num => Ok(Json.toJson(num)))
  }

}
