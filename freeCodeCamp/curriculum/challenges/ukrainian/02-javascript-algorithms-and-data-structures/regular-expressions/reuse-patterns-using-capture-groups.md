---
id: 587d7dbb367417b2b2512baa
title: Повторне використання шаблонів за допомогою груп
challengeType: 1
forumTopicId: 301364
dashedName: reuse-patterns-using-capture-groups
---

# --description--

Припустімо, ви хочете знайти збіг для слова, що трапляється кілька разів, як у прикладі нижче.

```js
let repeatStr = "row row row your boat";
```

Ви можете використати `/row row row/`, але що робити, якщо ви не знаєте, яке точно слово повторюється? <dfn>Capture groups</dfn> можна використовувати для пошуку повторюваних підрядків.

Групи захоплення створюються за допомогою оточення візерунка регулярного виразу, що має бути зафіксованим в дужках. В такому разі ціль полягає у захваті слова, що складається зі алфавітно-цифрових символів, отже група захоплення має `\w+` позначатися дужками: `/(\w+)/`.

Підрядок, що відповідає групі зберігається до тимчасової "змінної", доступ до якої можна отримати у межах одного й того ж самого регулярного виразу за допомогою зворотнього слешу і номера групи захоплення (напр. `\1`). Групи захоплення автоматично нумеруються за порядком запуску (зліва направо), починаючи з 1.

Наданий приклад дорівнює тричі розділеному пробілами слову:

```js
let repeatRegex = /(\w+) \1 \1/;
repeatRegex.test(repeatStr); // Returns true
repeatStr.match(repeatRegex); // Returns ["row row row", "row"]
```

Використовуючи метод `.match()` в рядку, можна повернути масив і відповідний підрядок разом з захопленими ним групами.


# --instructions--

Для збігання рядку, що складається лише з одного й того ж самого номера, який повторюється тільки три рази і розділений одинарними пробілами, використовуйте групи захоплення в `reRegex`.

# --hints--

Ваш регулярний вираз має користуватися класом скорочених символів для цифр.

```js
assert(reRegex.source.match(/\\d/));
```

Ваш регулярний вираз має повторно використовувати групу захоплення двічі.

```js
assert(reRegex.source.match(/\\1|\\2/g).length >= 2);
```

Регулярний вираз повинен збігатися з рядком `42 42 42`.

```js
reRegex.lastIndex = 0;
assert(reRegex.test('42 42 42'));
```

Регулярний вираз повинен збігатися з рядком `100 100 100`.

```js
reRegex.lastIndex = 0;
assert(reRegex.test('100 100 100'));
```

Регулярний вираз не повинен збігатися з рядком `42 42 42 42`.

```js
assert.equal('42 42 42 42'.match(reRegex.source), null);
```

Регулярний вираз не повинен збігатися з рядком `42 42`.

```js
assert.equal('42 42'.match(reRegex.source), null);
```

Регулярний вираз не повинен збігатися з рядком `101 102 103`.

```js
reRegex.lastIndex = 0;
assert(!reRegex.test('101 102 103'));
```

Регулярний вираз не повинен збігатися з рядком `1 2 3`.

```js
reRegex.lastIndex = 0;
assert(!reRegex.test('1 2 3'));
```

Регулярний вираз повинен збігатися з рядком `10 10 10`.

```js
reRegex.lastIndex = 0;
assert(reRegex.test('10 10 10'));
```

Your regex should not match the string `42\t42\t42`.

```js
reRegex.lastIndex = 0;
assert(!reRegex.test('42\t42\t42'));
```

Your regex should not match the string `42  42  42`.

```js
reRegex.lastIndex = 0;
assert(!reRegex.test('42  42  42'));
```

# --seed--

## --seed-contents--

```js
let repeatNum = "42 42 42";
let reRegex = /change/; // Change this line
let result = reRegex.test(repeatNum);
```

# --solutions--

```js
let repeatNum = "42 42 42";
let reRegex = /^(\d+) \1 \1$/;
let result = reRegex.test(repeatNum);
```
