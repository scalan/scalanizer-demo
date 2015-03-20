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

  def toRepExpr(c: whitebox.Context)(expr: c.Tree): c.Tree = {
    import c.universe._

    expr match {
      case q"$expr: $tpt" =>
        val reptpt = toRepType(c)(tpt)
        q"$expr: $reptpt"
      case q"if ($cond) $thenexpr else $elseexpr" =>
        q"IF ($cond) THEN {$thenexpr} ELSE {$elseexpr}"
      case q"${Literal(Constant(c))}" => q"toRep(${Literal(Constant(c))})"
      case _ => expr
    }
  }

  def toRepParam(c: whitebox.Context)(param: c.Tree): c.Tree = {
    import c.universe._

    param match {
      case q"$mods val $name: $tpt = $rhs" =>
        val reptpt = toRepType(c)(tpt)
        val reprhs = toRepExpr(c)(rhs)

        q"$mods val $name: $reptpt = $reprhs"
      case _ => param
    }
  }

  def toRepStats(c: whitebox.Context)(stats: List[c.Tree]): List[c.Tree] = {
    import c.universe._

    stats.map((stat: c.Tree) => stat match {
      case q"$mods def $tname[..$tparams](...$paramss): $tpt = $expr" =>
        val reptpt = toRepType(c)(tpt)
        val repparamss = paramss.map(_.map(param => toRepParam(c)(param)))
        val repexpr = toRepExpr(c)(expr)

        q"$mods def $tname[..$tparams](...$repparamss): $reptpt = $repexpr"
      case q"$mods var $name: $tpt = $rhs" =>
        val reptpt = toRepType(c)(tpt)
        val reprhs = toRepExpr(c)(rhs)

        q"$mods var $name: $reptpt = $reprhs"
      case q"$mods val $name: $tpt = $rhs" =>
        val reptpt = toRepType(c)(tpt)
        val reprhs = toRepExpr(c)(rhs)

        q"$mods val $name: $reptpt = $reprhs"
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
        val repStats = toRepStats(c)(stats)
        val res = q"""
            $mods trait $tpname[..$tparams]
            extends { ..$earlydefns } with ..$parents with Reifiable[$tpname[..$tparams]]
               { $self => ..$repStats }
            """
        print(res)
        res
      case q"""
            $mods class $tpname[..$tparams] $ctorMods(...$paramss)
            extends { ..$earlydefns } with ..$parents
            { $self => ..$stats }
            """
           =>
        val repStats = toRepStats(c)(stats)
        val repparamss = paramss.map(_.map(param => toRepParam(c)(param)))
        val res = q"""
            abstract class $tpname[..$tparams] $ctorMods(...$repparamss)
            extends { ..$earlydefns } with ..$parents
            { $self => ..$repStats }
            """
        print(res)
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

class ScalanType extends StaticAnnotation {
  def macroTransform(annottees: Any*) = macro ScalanMacros.impl
}
