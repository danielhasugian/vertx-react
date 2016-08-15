INSTRUCTION

1. Instal node js sebelum install react (referensi instalasi: tutorialspoint).
2. Untuk command npm init, isi field yang perlu aja (contoh liat di gambar). Field yang di skip (cara nyekipnya langsung tekan enter):
   entry point
   git repository
   keywords
   license
3. yes ==> enter, lanjut instalasi kaya tutorial point.
4. Untuk instalasi babel, di save aja (tambahin command --save), kaya gini:
   npm install babel-core --save
   npm install babel-loader --save
   npm install babel-preset-react --save
   npm install babel-preset-es2015 --save
5. Kalo command touch ga bisa jalan, bikin manual aja file index.html, App.jsx, main.js, and webpack.config.js
6. Kalo mau contoh file2 yang udah jalan bisa di liat disini.
7. Untuk run server, di cmd nodejs ketik command npm start.
8. Buka di browser localhost:8383 (port bisa di edit di webpack config)