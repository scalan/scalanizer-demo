import scala.language.experimental.macros
import scala.reflect.macros.whitebox
import scala.annotation.StaticAnnotation

object ScalanMacros {
  def toRep(c: whitebox.Context)(tree: c.Tree): c.Tree = {
    import c.universe._

    tree match {
      case q"""
            trait $tpname extends ..$parents { $self => ..$stats }
           """ =>
        print("Bingo")
        tree
      case _ => tree
    }
  }

  def impl(c: whitebox.Context)(annottees: c.Expr[Any]*): c.Expr[Any] = {
    import c.universe._

    val wrapped = annottees.map(exp => toRep(c)(exp.tree)).toList

    if (wrapped.length <= 1)
      c.Expr[Any](wrapped.head)
    else
      c.Expr[Any](Block(wrapped, Literal(Constant(()))))
  }
}

class staged extends StaticAnnotation {
  def macroTransform(annottees: Any*) = macro ScalanMacros.impl
}
