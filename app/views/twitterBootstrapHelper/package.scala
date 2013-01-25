package views.twitterBootstrapHelper

import views.html.helper._
import views.html.twitterBootstrapHelper.twitterFieldConstructor

package object twitterBootstrapHelper {
  implicit val twitterBootstrapHelperField = new FieldConstructor {
    def apply(elts: FieldElements) = twitterFieldConstructor(elts)
  }
}