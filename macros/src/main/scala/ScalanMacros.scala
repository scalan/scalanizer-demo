import scala.reflect.macros._
import scala.language.experimental.macros
import scala.annotation.StaticAnnotation

object ScalanMacro {
  def impl(c: Context)(annottees: c.Expr[Any]*): c.Expr[Any] = {
    import c.universe._
    import Flag._

    annottees.head
  }
}

class staged extends StaticAnnotation {
  def macroTransform(annottees: Any*) = macro ScalanMacro.impl
}
