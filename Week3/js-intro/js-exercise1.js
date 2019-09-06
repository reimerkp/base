
let maxLength = function (array) {
    let x = "";
    for (const value of array.entries()) {
        if (value.length > x.length) {
            x = value;
        }
    }
    return array.indexOf(x);
}

let reverseArray = function (array) {
    return array.reverse();
}

let vowelCount = function (string) {
    let splitString = string.split('');
    let vowelC = 0;
    for (const value of splitString) {
        switch (value) {
            case "a": case "e": case "i": case "o": case "u":
                vowelC++;
        }
    }
    return vowelC;
}

let validateEmail = function (string) {
    let regex = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
    return regex.test(string);
}


let removeChar = function (string, index) {
    let newString = string.slice(0, index) + string.slice(index + 1);
    return newString;
}

let swap = function (arr, first_Index, second_Index) {
    var temp = arr[first_Index];
    arr[first_Index] = arr[second_Index];
    arr[second_Index] = temp;
}

let bubbleSort = function (numArray) {

    var len = numArray.length,
        i, j, stop;

    for (i = 0; i < len; i++) {
        for (j = 0, stop = len - i; j < stop; j++) {
            if (numArray[j] > numArray[j + 1]) {
                swap(numArray, j, j + 1);
            }
        }
    }

    return numArray;
}

let isEven = function (someNum) {
    return Number.isInteger(someNum / 2);
}

let isPalindrome = function (string) {
    let stringArray = string.split("");
    stringArray = stringArray.reverse();
    var reverseString = stringArray.join("");
    return reverseString == string;
}


let isLeapYear = function (date) {
    return (date % 100 === 0) ? (date % 400 === 0) : (date % 4 === 0);
}

let printShape = function (shape, height, character) {
    let count = 0;
    switch (shape) {
        case "Square":
            for (let i = 0; i < height; i++) {
                let line = "";
                for (let j = 0; j < 3; j++) {
                    line += character;
                }
                console.log(line);
            }
            break;
        case "Triangle":
            count = 0;
            for (let i = 0; i < height; i++) {
                count++;
                let line = "";
                for (let j = 0; j < count; j++) {
                    line += character;
                }
                console.log(line);
            }
            break;
        case "Diamond":
            count = 1;
            for (let i = 0; i < height; i++) {
                let line = "";
                for (let j = 0; j < count; j++) {
                    line += character;
                }
                console.log(line);
                count += 2;
                if (count > height) { count -= 4; break; }
            }
            for (let j = 0; j < height; j++) {
                let line = "";
                for (let i = 0; i < count; i++) {
                    line += character;
                }
                console.log(line);
                count -= 2;
                if (count < 0) {
                    break;
                }
            }
            break;
    }
}


let rotate = function (array, n) {
    for (let i = 0; i < n; i++) {
        rotateArray(array);
    }
    return array;
}

let rotateArray = function (array) {
    let hold = array[0];
    for (let i = 0; i < array.length - 1; i++) {
        array[i] = array[i + 1];
    }
    array[array.length - 1] = hold;
    return array;
}

let balanced = function (string) {
    let sArray = string.split('');
    let pCount = 0;
    let bCount = 0;
    let cCount = 0;
    for (const value of sArray) {
        switch (value) {
            case "{":
                cCount++;
                break;
            case "(":
                pCount++;
                break;
            case "[":
                bCount++;
                break;
            case "}":
                cCount--;
                break;
            case ")":
                pCount--;
                break;
            case "]":
                bCount--;
                break;
        }
    }
    if (pCount == 0 && cCount == 0 && bCount == 0) {
        return true;
    } else {
        return false;
    }
}