(ns html-templates.core)

(defn attrs
  [attr-map]
  {:pre [(or (map? attr-map)
             (nil? attr-map))]}
  (->> attr-map
    (mapcat (fn [[k v]] [\space (name k) "=\"" v "\""]))
    (apply str)))

(defn html
  [x]
  {:pre [(if (sequential? x)
           (some #(-> x first %) [keyword? symbol? string?])
           (not (map? x)))]
   :post [(string? %)]}
  (if-not (sequential? x)
    (str x)
    (let [[tag & body] x
          [attr-map body] (if (map? (first body))
                            [(first body) (rest body)]
                            [nil body])]
      (str "<" (name tag) (attrs attr-map) ">"
           (apply str (map html body))
           "</" (name tag) ">"))))
