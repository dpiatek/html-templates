(ns html-templates.core-test
  (:require [clojure.test :refer :all]
            [html-templates.core :refer :all]
            [clojure.string]))

(defmacro are* [f & body]
  `(are [x# y#] (~'= (~f x#) y#)
     ~@body))

; (declare html attrs)

(deftest test-html
  (are* html
    [:html]
    "<html></html>"

    [:a [:b]]
    "<a><b></b></a>"

    [:a {:href "/"} "Home"]
    "<a href=\"/\">Home</a>"

    [:div "foo" [:span "bar"] "baz"]
    "<div>foo<span>bar</span>baz</div>"

    (list* :ul (for [author [\1 \2 \3]] [:li author]))
    "<ul><li>1</li><li>2</li><li>3</li></ul>"))

(deftest test-attrs
  (are* (comp clojure.string/trim attrs)
    nil ""

    {:foo "bar"}
    "foo=\"bar\""

    (sorted-map :a "b" :c "d")
    "a=\"b\" c=\"d\""))
