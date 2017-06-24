package system

/**
  * Created by sarathraj on 23/06/17.
  */

import javax.inject.Inject

import akka.actor.ActorSystem
import play.api.libs.concurrent.CustomExecutionContext

import scala.concurrent.ExecutionContext
trait MyExecutionContext extends ExecutionContext

class MyExecutionContextImpl @Inject()(system: ActorSystem)
  extends CustomExecutionContext(system, "my.executor") with MyExecutionContext