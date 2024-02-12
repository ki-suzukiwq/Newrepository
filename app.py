import sqlite3
from flask import Flask,render_template,request,g

app = Flask(__name__)

def get_db():
    if 'db' not in g:
        # データベースをオープンしてFlaskのグローバル変数に保存
        g.db = sqlite3.connect('TestDB.db')
    return g.db

#「/templates」へアクセスがあった場合に、index.htmlを返す
@app.route('/')
def index():
    return render_template("index.html")

#参照画面
@app.route('/Getitem', methods=["GET"])
def Getitem():
    # データベースを開く
    con = get_db()

    # 顧客一覧を読み込み
    cur = con.execute("select * from 顧客一覧 order by コード")
    data = cur.fetchall()
    con.close()

    return render_template('Getitem.html', data = data)


#登録画面
@app.route('/Putitem', methods=["GET"])
def Putitem():
    # データベースを開く
    con = get_db()

    # テーブル「顧客一覧」の有無を確認
    cur = con.execute("select count(*) from sqlite_master where TYPE='table' AND name='顧客一覧'")

    for row in cur:
        if row[0] == 0:
            # テーブル「顧客一覧」がなければ作成する
            cur.execute("CREATE TABLE 顧客一覧(コード INTEGER PRIMARY KEY, 顧客名 TEXT, 電話番号 TEXT,メールアドレス TEXT)")

    # 顧客一覧を読み込み
    #cur = con.execute("select * from 顧客一覧 order by コード")
    #data = cur.fetchall()
    con.close()

    return render_template('Putitem.html')

@app.route('/result', methods=["POST"])
def result_post():
    # テンプレートから新規登録する顧客名と電話番号を取得
    name = request.form["name"]
    phone = request.form["phone"]
    mladdr = request.form["mladdr"]

    # データベースを開く
    con = get_db()

    cur_max_code = con.execute("SELECT MAX(コード) AS max_code FROM 顧客一覧")
    row = cur_max_code.fetchone()

    if row and row[0] is not None:
        new_code = row[0] + 1
    else:
    # テーブルが存在しない場合や結果が空の場合、新しいコードに1を設定
        new_code = 1

    #fechoneでDB接続しているのでリソース解放
    cur_max_code.close()

    # 登録処理
    sql = "INSERT INTO 顧客一覧(コード, 顧客名, 電話番号, メールアドレス) VALUES({}, '{}', '{}', '{}')".format(new_code, name, phone, mladdr)

    print(phone)
    con.execute(sql)
    con.commit()

    # 一覧再読み込み
    cur = con.execute("select * from 顧客一覧 order by コード")
    data = cur.fetchall()
    con.close()

    return render_template('Putitem.html', data = data)


@app.route('/delete', methods=["POST"])
def delete_post():
    # データベースを開く
    con = get_db()

    # フォームから削除対象の顧客名を取得
    delete_name = request.form.get("delete_name")

    # 削除処理
    sql = "DELETE FROM 顧客一覧 WHERE 顧客名='{}'".format(delete_name)
    con.execute(sql)
    con.commit()

    # 一覧再読み込み
    cur = con.execute("SELECT * FROM 顧客一覧 ORDER BY コード")
    data = cur.fetchall()
    con.close()

    return render_template('Getitem.html', data=data)

if __name__ == '__main__':
    app.debug = True
    app.run(host='0.0.0.0', port=8080)