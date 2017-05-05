konserve-firebase
=================

Firebase backend for the [konserve](https://github.com/replikativ/konserve) 
library.
This is Clojurescript only.

Usage
-----

[![Clojars Project](https://img.shields.io/clojars/v/konserve-firebase.svg)](https://clojars.org/konserve-firebase)
[![CircleCI](https://circleci.com/gh/lsenta/konserve-firebase.svg?style=svg)](https://circleci.com/gh/lsenta/konserve-firebase)

The library provides 2 namespaces:
- `konserve-firebase.core`: light layer around the Firebase javascript lib,
- `konserve-firebase.store`: konserve storage backend.


TODOs
-----

- [ ] Have the tests pass
- [ ] Enable the source map when compiling with advanced optimizations
    - right now, `:source-map "target/release/js/konserve-firebase.js.map"`
      will raise cryptic errors about not being in a certain directory.


Contribute
----------

```bash
git clone git@github.com:lsenta/konserve-firebase.git
cp env/konserve_firebase/test_env.cljs.example env/konserve_firebase/test_env.cljs
vim env/konserve_firebase/test_env.cljs # put your firebase config
lein cljsbuild once test
```


License
-------

MIT License

Copyright (c) 2017 Laurent Senta

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
