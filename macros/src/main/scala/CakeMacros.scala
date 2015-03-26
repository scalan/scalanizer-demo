package scalan.paradise

import scala.language.experimental.macros
import scala.reflect.macros.whitebox
import scala.annotation.StaticAnnotation

object CakeSliceMacros {
  def addDefaultElem(c: whitebox.Context)(stats: List[c.Tree]) = {
    import c.universe._

    val defaultElem = q"implicit def defaultSegmElem: Elem[Segm] = element[Interval].asElem[Segm]"
    stats :+ defaultElem
  }
  def toCake(c: whitebox.Context)(tree: c.Tree): c.Tree = {
    import c.universe._

    tree match {
      case q"""$mods trait $tpname[..$tparams]
               extends { ..$earlydefns } with ..$parents
               { $self => ..$stats }
             """
      =>
        val newstats = addDefaultElem(c)(stats)
        val res =
          q"""
            $mods trait $tpname[..$tparams]
            extends { ..$earlydefns } with ..$parents with Base with BaseTypes
               { self: SegmsDsl => ..$newstats }
            """
        //print(res)
        res
      case _ => tree
    }
  }

  def impl(c: whitebox.Context)(annottees: c.Expr[Any]*) = {
    val caked = annottees.map(exp => toCake(c)(exp.tree))

    c.Expr(caked.head)
  }
}

class CakeSlice extends StaticAnnotation {
  def macroTransform(annottees: Any*) = macro CakeSliceMacros.impl
}

