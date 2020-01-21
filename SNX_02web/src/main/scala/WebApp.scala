
import java.util.HashMap

import java.util.Map

import org.SNXa.http.HttpHandlerImpl

import org.SNXa.http.Route1

import org.apache.SNX.IRoute

import org.apache.SNX.SNX

import org.apache.SNX.http.DefaultMainHttpServer

object WebApp {

  var _srv: DefaultMainHttpServer = _

  def main(args: Array[String]): Unit = {
    new SNX()
    println("start " + System.getProperty("user.dir"))
    val routes: Map[String, IRoute] = new HashMap[String, IRoute]()
    // we add one route for our HTTP server
    val r1: IRoute = new Route1(null)
    routes.put(r1.getPath, r1)
    // routes done
    val docRoot: String = System.getProperty("user.dir") + "/www"
    _srv = new DefaultMainHttpServer(new HttpHandlerImpl(routes, docRoot))
    _srv.start(8888)
    println("end")
  }

}