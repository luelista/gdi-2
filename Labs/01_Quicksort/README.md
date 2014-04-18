# Lab 1 - QuickSort

Submission Deadline: May 11, 2014 @ 23:59

## how it's done

...


## it's way cooler in javascript!

```js
function ac() { // Array Concat
	return Array.prototype.concat.apply([], arguments);
}
function qs(a) { // Quick Sort
	if (a.length == 0) return [];
	else if (a.length == 1) return a;
	else {
		var pivot = a.shift();
		return ac(
			qs(a.filter(function(x) { return x <= pivot; })),
			[pivot],
			qs(a.filter(function(x) { return x > pivot; }))
		       );
	}
}
function randar(max, len) {
	var ar = [];
	for(var i = 0; i < len; i++)
		ar.push(Math.floor(Math.random()*max));
	return ar;
}
function testIt(number) {
	var a = randar(number, number);
	var start = +new Date();
	var b = qs(a);
	var end = +new Date();
	for(var i=0, j=0; i < number; i++)
		if (j <= b[i]) j = b[i]; else console.log("ERROR");
	console.log("Took "+(end-start)+" ms");
}
```

