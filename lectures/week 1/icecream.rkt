;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-intermediate-lambda-reader.ss" "lang")((modname icecream) (read-case-sensitive #t) (teachpacks ((lib "image.rkt" "teachpack" "2htdp") (lib "universe.rkt" "teachpack" "2htdp"))) (htdp-settings #(#t constructor repeating-decimal #f #t none #f ((lib "image.rkt" "teachpack" "2htdp") (lib "universe.rkt" "teachpack" "2htdp")) #f)))
;; An IceCream is one of:
;; -- EmptyServing
;; -- Scooped
 
;;An EmptyServing is a (make-empty-serving Boolean)
(define-struct empty-serving (cone))
 
;;A Scooped is a (make-scooped IceCream String)
(define-struct scooped (more flavor))