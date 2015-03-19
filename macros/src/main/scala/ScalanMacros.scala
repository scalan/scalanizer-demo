import scala.language.experimental.macros
import scala.reflect.macros.whitebox
import scala.annotation.StaticAnnotation

object ScalanMacros {
  def toRepStats(c: whitebox.Context)(stats: List[c.Tree]): List[c.Tree] = {
    import c.universe._

    stats.map((stat: c.Tree) => stat match {
      case q"$mods def $tname[..$tparams](...$paramss): $tpt = $expr" =>
        print("paramss = " + showRaw(paramss))
        q"$mods def $tname[..$tparams](...$paramss): $tpt = $expr"
      case _ => stat
    })
  }
  def toRep(c: whitebox.Context)(tree: c.Tree): c.Tree = {
    import c.universe._

    tree match {
      case q"""$mods trait $tpname[..$tparams]
               extends { ..$earlydefns } with ..$parents
               { $self => ..$stats }
             """
           =>
        print("Bingo")
        val repStats = toRepStats(c)(stats)
        val res =
           q"""$mods trait $tpname[..$tparams]
            extends { ..$earlydefns } with ..$parents with Base with BaseTypes
               { self: Scalan => ..$repStats }
            """
        println(res)
        res
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
