import scala.language.experimental.macros
import scala.reflect.macros.whitebox
import scala.annotation.StaticAnnotation

object ScalanMacros {
  def toRepType(c: whitebox.Context)(tp: c.Tree): c.Tree = {
    import c.universe._

    tp match {
      case tq"" => tp
      case tq"$tpname" => tq"Rep[$tpname]"
    }
  }

  def toRepParam(c: whitebox.Context)(param: c.Tree): c.Tree = {
    import c.universe._

    param match {
      case q"$mods val $name: $tpt = $rhs" =>
        val reptpt = toRepType(c)(tpt)
        q"$mods val $name: $reptpt = $rhs"
      case _ => param
    }
  }

  def toRepStats(c: whitebox.Context)(stats: List[c.Tree]): List[c.Tree] = {
    import c.universe._

    stats.map((stat: c.Tree) => stat match {
      case q"$mods def $tname[..$tparams](...$paramss): $tpt = $expr" =>
        val reptpt = toRepType(c)(tpt)
        val repparamss = paramss.map(_.map(param => toRepParam(c)(param)))

        print("expr = " + showRaw(expr))
        q"$mods def $tname[..$tparams](...$repparamss): $reptpt = $expr"
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
        res
      case _ => tree
    }
  }

  def impl(c: whitebox.Context)(annottees: c.Expr[Any]*) = {
    import c.universe._

    val wrapped = annottees.map(exp => toRep(c)(exp.tree)).toList

    if (wrapped.length <= 1)
      c.Expr(wrapped.head)
    else
      c.Expr(Block(wrapped, Literal(Constant(()))))
  }
}

class staged extends StaticAnnotation {
  def macroTransform(annottees: Any*) = macro ScalanMacros.impl
}
