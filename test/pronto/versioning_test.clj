(ns pronto.versioning-test
  (:require [clojure.test :refer :all]
            [pronto.core :refer [defmapper] :as p]
            [pronto.utils :as u])
  (:import (clojure.lang ExceptionInfo)
           (java.util UUID)
           [protogen.generated Versioning$V1 Versioning$V2 Versioning$UUID]))

(defmapper mapper [Versioning$V1 Versioning$V2]
  :key-name-fn u/->kebab-case
  :enum-value-fn u/->kebab-case
  :encoders {Versioning$UUID
             {:from-proto #(try
                             (UUID/fromString (.getValue ^Versioning$UUID %))
                             (catch Exception _))
              :to-proto   #(let [b (Versioning$UUID/newBuilder)]
                             (.setValue b (str %))
                             (.build b))}})

(deftest types-evolving-test
  (let [v2 (p/proto-map mapper Versioning$V2
                        :a1 "a1"
                        :a2 5
                        :a3 :e2-val3
                        :b1 "b1"
                        :b2 6
                        :b3 {:c 7}
                        :b4 [{:c 8} {:c 9}]
                        :b5 {"q" {:c 10}}
                        :b6 "b6"
                        :b7 #uuid "61be663a-ff40-4199-8489-18da7546cb81"
                        :bb 11.11)
        v1 (p/bytes->proto-map mapper Versioning$V1 (p/proto-map->bytes v2))]
    (testing "get fields"
          (is (= (:a1 v1) "a1"))
          (is (= (:a2 v1) 5))
          (is (= (:a3 v1) :unrecognized))
          (is (nil? (p/which-one-of v1 :thing)))
          (is (nil? (p/one-of v1 :thing))))

    (testing "set fields with legal value"
      (let [result (assoc v1
                    :a1 "a11"
                    :a2 55
                    :a3 :e1-val2
                    :num 777)
            expected {:a1 "a11" :a2 55 :a3 :e1-val2 :num 777 :str nil :person nil}]
        (is (= (:a1 result) (:a1 expected)))
        (is (= (:a2 result) (:a2 expected)))
        (is (= (:a3 result) (:a3 expected)))
        (is (= (:num result) (:num expected)))))

    (testing "set fields with illegal value"
      (is (thrown? ExceptionInfo
                   (assoc v1 :a3 :unrecognized))))

    (testing "new->old->new unknown fields are preserved"
      ;; Define a custom equality function for Protobuf maps that ignores nil vs empty string differences
      (let [proto-equal? (fn [m1 m2]
                           (let [normalize (fn [m]
                                             ;; Replace empty strings with nil for comparison purposes
                                             (into {} (map (fn [[k v]] 
                                                            [k (if (and (string? v) (empty? v)) nil v)])) 
                                                   m))]
                             (= (normalize m1) (normalize m2))))]
        (is (proto-equal? 
              (->> (assoc v1 :a1 "a22")
                  p/proto-map->bytes
                  (p/bytes->proto-map mapper Versioning$V2))
              (assoc v2 :a1 "a22")))))))
