'use strict'
/*
let arrs=[1,2,3]
console.log(typeof(arrs))
console.log(arrs)
//add first
arrs.unshift('unshift')
console.log(arrs)
//add last
arrs.push('push')
console.log(arrs)
//replace and insert
arrs.splice(2,1,'splice','splice1')
console.log(arrs)
//insert
arrs.splice(2,0,'splice','splice1')
console.log(arrs)
//remove specific index
arrs.splice(2.2)
console.log(arrs)
//remove 
arrs.pop(2)
console.log(arrs)
*/
//reference or deep copy
// let scores=[10,50,60,40]
// let bonusScore=scores
// console.log(scores)
// bonusScore.pop();
// console.log(scores)
//shallow copy
let scs=[10,50,40]
let bnsc=scs.slice()
let bunusScore=Array.from(scs)
console.log(bnsc)
console.log(bunusScore)
let spread=[...scs]
console.log("using spread: "+spread)