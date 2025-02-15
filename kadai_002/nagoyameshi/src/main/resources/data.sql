-- rolesテーブル
INSERT IGNORE INTO roles (id, name) VALUES (1, 'ROLE_GENERAL');
INSERT IGNORE INTO roles (id, name) VALUES (2, 'ROLE_ADMIN');

-- usersテーブル
INSERT IGNORE INTO users (id, name, furigana, postal_code, address, phone_number, email, password, role_id, enabled) VALUES (1, '侍 太郎', 'サムライ タロウ', '101-0022', '東京都千代田区神田練塀町300番地', '090-1234-5678', 'taro.samurai@example.com', '$2a$10$2JNjTwZBwo7fprL2X4sv.OEKqxnVtsVQvuXDkI8xVGix.U3W5B7CO', 1, true);
INSERT IGNORE INTO users (id, name, furigana, postal_code, address, phone_number, email, password, role_id, enabled) VALUES (2, '侍 花子', 'サムライ ハナコ', '101-0022', '東京都千代田区神田練塀町300番地', '090-1234-5678', 'hanako.samurai@example.com', '$2a$10$2JNjTwZBwo7fprL2X4sv.OEKqxnVtsVQvuXDkI8xVGix.U3W5B7CO', 2, true);
INSERT IGNORE INTO users (id, name, furigana, postal_code, address, phone_number, email, password, role_id, enabled) VALUES (3, '侍 義勝', 'サムライ ヨシカツ', '638-0644', '奈良県五條市西吉野町湯川X-XX-XX', '090-1234-5678', 'yoshikatsu.samurai@example.com', 'password', 1, false);
INSERT IGNORE INTO users (id, name, furigana, postal_code, address, phone_number, email, password, role_id, enabled) VALUES (4, '侍 幸美', 'サムライ サチミ', '342-0006', '埼玉県吉川市南広島X-XX-XX', '090-1234-5678', 'sachimi.samurai@example.com', 'password', 1, false);
INSERT IGNORE INTO users (id, name, furigana, postal_code, address, phone_number, email, password, role_id, enabled) VALUES (5, '侍 雅', 'サムライ ミヤビ', '527-0209', '滋賀県東近江市佐目町X-XX-XX', '090-1234-5678', 'miyabi.samurai@example.com', 'password', 1, false);
INSERT IGNORE INTO users (id, name, furigana, postal_code, address, phone_number, email, password, role_id, enabled) VALUES (6, '侍 正保', 'サムライ マサヤス', '989-1203', '宮城県柴田郡大河原町旭町X-XX-XX', '090-1234-5678', 'masayasu.samurai@example.com', 'password', 1, false);
INSERT IGNORE INTO users (id, name, furigana, postal_code, address, phone_number, email, password, role_id, enabled) VALUES (7, '侍 真由美', 'サムライ マユミ', '951-8015', '新潟県新潟市松岡町X-XX-XX', '090-1234-5678', 'mayumi.samurai@example.com', 'password', 1, false);
INSERT IGNORE INTO users (id, name, furigana, postal_code, address, phone_number, email, password, role_id, enabled) VALUES (8, '侍 安民', 'サムライ ヤスタミ', '241-0033', '神奈川県横浜市旭区今川町X-XX-XX', '090-1234-5678', 'yasutami.samurai@example.com', 'password', 1, false);
INSERT IGNORE INTO users (id, name, furigana, postal_code, address, phone_number, email, password, role_id, enabled) VALUES (9, '侍 章緒', 'サムライ アキオ', '739-2103', '広島県東広島市高屋町宮領X-XX-XX', '090-1234-5678', 'akio.samurai@example.com', 'password', 1, false);
INSERT IGNORE INTO users (id, name, furigana, postal_code, address, phone_number, email, password, role_id, enabled) VALUES (10, '侍 祐子', 'サムライ ユウコ', '601-0761', '京都府南丹市美山町高野X-XX-XX', '090-1234-5678', 'yuko.samurai@example.com', 'password', 1, false);
INSERT IGNORE INTO users (id, name, furigana, postal_code, address, phone_number, email, password, role_id, enabled) VALUES (11, '侍 秋美', 'サムライ アキミ', '606-8235', '京都府京都市左京区田中西春菜町X-XX-XX', '090-1234-5678', 'akimi.samurai@example.com', 'password', 1, false);
INSERT IGNORE INTO users (id, name, furigana, postal_code, address, phone_number, email, password, role_id, enabled) VALUES (12, '侍 信平', 'サムライ シンペイ', '673-1324', '兵庫県加東市新定X-XX-XX', '090-1234-5678', 'shinpei.samurai@example.com', 'password', 1, false);

-- shopsテーブルへのデータ挿入
INSERT IGNORE INTO shops (id, category_id, name, image, description, business_hours, price, postal_code, address, phone_number, regular_holiday) VALUES
(1, 1, 'ひつまぶし本店', 'house01.jpg', '名古屋名物のひつまぶしを提供する老舗。香ばしく焼き上げた鰻が絶品。', '11:00-21:00', 3000, '460-0008', '愛知県名古屋市中区栄X-XX-XX', '052-123-4567', '水曜'),
(2, 1, '味噌カツ亭', 'house02.jpg', '八丁味噌を使った秘伝のタレが自慢の味噌カツ専門店。', '10:30-22:00', 1500, '450-0002', '愛知県名古屋市中村区名駅X-XX-XX', '052-234-5678', '火曜'),
(3, 3, '手羽先唐揚げの店', 'house03.jpg', 'スパイシーなタレで味付けされた名古屋名物の手羽先唐揚げが人気。', '16:00-24:00', 1200, '460-0011', '愛知県名古屋市中区大須X-XX-XX', '052-345-6789', '月曜'),
(4, 1, '味噌煮込みうどん処', 'house04.jpg', 'コシのあるうどんと濃厚な味噌スープが特徴の味噌煮込みうどん専門店。', '11:00-20:30', 1300, '464-0850', '愛知県名古屋市千種区今池X-XX-XX', '052-456-7890', '木曜'),
(5, 1, 'きしめん亭', 'house05.jpg', 'つるっとしたのど越しの良いきしめんを提供する名店。', '10:00-20:00', 1100, '450-0003', '愛知県名古屋市中村区名駅南X-XX-XX', '052-567-8901', '金曜'),
(6, 4, '台湾ラーメン店', 'house06.jpg', '辛さがクセになる名古屋名物の台湾ラーメンを提供。', '18:00-2:00', 800, '460-0021', '愛知県名古屋市中区大須X-XX-XX', '052-901-2345', 'なし'),
(7, 5, 'どて煮屋', 'house07.jpg', '濃厚な味噌でじっくり煮込んだどて煮が名物の居酒屋。', '17:00-23:30', 900, '460-0022', '愛知県名古屋市中区金山X-XX-XX', '052-890-1234', '月曜'),
(8, 5, 'エビフライ専門店', 'house08.jpg', '名古屋名物のジャンボエビフライが味わえる専門店。', '11:00-22:00', 2500, '460-0012', '愛知県名古屋市中区新栄X-XX-XX', '052-789-0123', '火曜'),
(9, 5, '天むすの里', 'house09.jpg', '名古屋名物の天むすを提供。サクサクの天ぷらとご飯の絶妙なバランス。', '9:00-18:00', 1000, '460-0003', '愛知県名古屋市中区錦X-XX-XX', '052-678-9012', '日曜'),
(10, 1, '名古屋コーチン料理店', 'house10.jpg', '地鶏「名古屋コーチン」を使った料理が堪能できる専門店。', '11:30-21:00', 3500, '460-0005', '愛知県名古屋市中区丸の内X-XX-XX', '052-012-3456', '水曜'),
(11, 1, '名古屋おでん本舗', 'house01.jpg', '八丁味噌を使った名古屋風おでんが楽しめる専門店。', '17:00-23:00', 1200, '460-0001', '愛知県名古屋市中区丸の内X-XX-XX', '052-111-2222', '月曜'),
(12, 5, '幻の手羽先', 'house02.jpg', '秘伝のタレで味付けした手羽先が自慢の居酒屋。', '16:00-24:00', 1300, '460-0015', '愛知県名古屋市中区栄X-XX-XX', '052-222-3333', 'なし'),
(13, 3, '中華料理 味仙', 'house03.jpg', '名古屋名物・台湾ラーメンの元祖。ピリ辛スープがクセになる。', '11:00-23:00', 1000, '450-0004', '愛知県名古屋市中村区名駅X-XX-XX', '052-333-4444', '火曜'),
(14, 4, 'ラーメン寿がきや', 'house04.jpg', '名古屋のソウルフード、寿がきやラーメンの名店。', '10:00-21:00', 700, '460-0016', '愛知県名古屋市中区伏見X-XX-XX', '052-444-5555', '水曜'),
(15, 2, 'あんかけスパゲッティの店', 'house05.jpg', '名古屋独特のとろみのあるあんかけスパゲッティ専門店。', '11:00-20:30', 1100, '460-0024', '愛知県名古屋市中区錦X-XX-XX', '052-555-6666', '木曜'),
(16, 1, '鰻丼専門店 うな富士', 'house06.jpg', '極上の炭火焼き鰻を使った鰻丼の名店。', '11:00-20:00', 3500, '460-0031', '愛知県名古屋市中区千種X-XX-XX', '052-666-7777', '金曜'),
(17, 5, '地酒とどて焼き', 'house07.jpg', '名古屋名物のどて焼きを地酒とともに楽しめるお店。', '17:00-24:00', 1400, '460-0041', '愛知県名古屋市中区新栄X-XX-XX', '052-777-8888', '日曜'),
(18, 3, 'チャーハン専門店 炒王', 'house08.jpg', 'パラパラ炒飯が自慢の名古屋のチャーハン専門店。', '10:30-22:30', 900, '460-0051', '愛知県名古屋市中区今池X-XX-XX', '052-888-9999', 'なし'),
(19, 2, 'カレーうどん かつみや', 'house09.jpg', '名古屋名物のカレーうどんが楽しめるお店。', '11:00-21:30', 1000, '460-0061', '愛知県名古屋市中区大須X-XX-XX', '052-999-0000', '月曜'),
(20, 1, 'ひつまぶし・うなぎの里', 'house10.jpg', 'こだわりの鰻を使用したひつまぶしの名店。', '11:30-22:00', 4000, '460-0071', '愛知県名古屋市中区名駅X-XX-XX', '052-000-1111', '火曜'),
(21, 1, '名古屋しゃぶしゃぶ亭', 'house01.jpg', '名古屋コーチンと特製のごまだれで楽しむしゃぶしゃぶ専門店。', '17:00-23:00', 3800, '460-0081', '愛知県名古屋市中区栄X-XX-XX', '052-111-2233', '水曜'),
(22, 5, '手羽先専門店 赤から', 'house02.jpg', 'ピリ辛の赤からタレで味付けした手羽先が人気の居酒屋。', '16:00-24:00', 1500, '460-0082', '愛知県名古屋市中区錦X-XX-XX', '052-222-3344', 'なし'),
(23, 2, 'カフェ・モーニング名古屋', 'house03.jpg', '名古屋の喫茶店文化、ボリューム満点のモーニングセットが人気。', '7:00-14:00', 800, '450-0005', '愛知県名古屋市中村区名駅X-XX-XX', '052-333-4455', '火曜'),
(24, 3, '中華飯店 金龍', 'house04.jpg', '名古屋の街中華の名店。特にチャーハンとエビチリが絶品。', '11:00-22:00', 1200, '460-0083', '愛知県名古屋市中区新栄X-XX-XX', '052-444-5566', '木曜'),
(25, 4, '台湾まぜそば本舗', 'house05.jpg', '名古屋発祥の台湾まぜそば専門店。濃厚なタレともちもちの麺が特徴。', '11:30-21:30', 1100, '460-0084', '愛知県名古屋市中区今池X-XX-XX', '052-555-6677', 'なし'),
(26, 1, 'みそおでんの里', 'house06.jpg', '八丁味噌でじっくり煮込んだ名古屋名物のみそおでんが楽しめるお店。', '17:00-23:00', 1200, '460-0085', '愛知県名古屋市中区金山X-XX-XX', '052-666-7788', '月曜'),
(27, 5, '味噌串カツの店', 'house07.jpg', '甘辛い味噌だれがかかった名古屋名物の串カツが絶品の居酒屋。', '17:00-24:00', 1000, '460-0086', '愛知県名古屋市中区栄X-XX-XX', '052-777-8899', '日曜'),
(28, 3, '上海料理 王府', 'house08.jpg', '本格上海料理が味わえる人気の中華料理店。', '11:00-22:30', 2500, '460-0087', '愛知県名古屋市中区伏見X-XX-XX', '052-888-9900', '水曜'),
(29, 2, '名古屋カツサンド専門店', 'house09.jpg', 'サクサクのカツと特製デミグラスソースのカツサンド専門店。', '10:00-20:00', 1300, '460-0088', '愛知県名古屋市中区名駅X-XX-XX', '052-999-0011', 'なし'),
(30, 1, '天ぷらと鰻の店', 'house10.jpg', 'サクサクの天ぷらと鰻料理を同時に楽しめる老舗。', '11:30-21:00', 3500, '460-0089', '愛知県名古屋市中区大須X-XX-XX', '052-000-1122', '火曜');


-- categoriesテーブルへのデータ挿入
INSERT IGNORE INTO categories (id, name) VALUES
(1, '和食'),
(2, '洋食'),
(3, '中華'),
(4, 'ラーメン'),
(5, '居酒屋');

-- reviewsテーブル
INSERT IGNORE INTO reviews (id, shop_id, user_id, comment) VALUES (1, 1, 1, 'ひつまぶしが絶品でした！また食べに来たいです。');
INSERT IGNORE INTO reviews (id, shop_id, user_id, comment) VALUES (2, 1, 2, '味噌カツがジューシーでご飯が進みました。');
INSERT IGNORE INTO reviews (id, shop_id, user_id, comment) VALUES (3, 1, 3, '名古屋コーチンの親子丼がふわとろで最高でした！');
INSERT IGNORE INTO reviews (id, shop_id, user_id, comment) VALUES (4, 1, 4, 'スタッフさんのおすすめ、手羽先がピリ辛でお酒にぴったりでした。');
INSERT IGNORE INTO reviews (id, shop_id, user_id, comment) VALUES (5, 1, 5, 'モーニングサービスの小倉トーストが甘くて美味しかったです。');
INSERT IGNORE INTO reviews (id, shop_id, user_id, comment) VALUES (6, 1, 6, 'きしめんの出汁が絶妙で、つるっと食べられました。');
INSERT IGNORE INTO reviews (id, shop_id, user_id, comment) VALUES (7, 1, 7, '天むすが軽くて食べやすく、お土産にもぴったりでした。');
INSERT IGNORE INTO reviews (id, shop_id, user_id, comment) VALUES (8, 1, 8, '味噌煮込みうどんがコク深くて体が温まりました。');
INSERT IGNORE INTO reviews (id, shop_id, user_id, comment) VALUES (9, 1, 9, '台湾ラーメンの辛さがクセになりそうです！');
INSERT IGNORE INTO reviews (id, shop_id, user_id, comment) VALUES (10, 1, 10, 'どて煮が味噌の風味たっぷりでお酒によく合いました。');
INSERT IGNORE INTO reviews (id, shop_id, user_id, comment) VALUES (11, 1, 11, 'エビフライが大きくてプリプリでした！');
INSERT IGNORE INTO reviews (id, shop_id, user_id, comment) VALUES (12, 1, 12, '名古屋名物が一通り楽しめるお店で大満足でした。');

 -- favoritesテーブル
 INSERT IGNORE INTO favorites (id, shop_id, user_id) VALUES (1, 1, 1);
 INSERT IGNORE INTO favorites (id, shop_id, user_id) VALUES (2, 2, 1);
 INSERT IGNORE INTO favorites (id, shop_id, user_id) VALUES (3, 3, 1);
 INSERT IGNORE INTO favorites (id, shop_id, user_id) VALUES (4, 4, 1);
 INSERT IGNORE INTO favorites (id, shop_id, user_id) VALUES (5, 5, 1);
 INSERT IGNORE INTO favorites (id, shop_id, user_id) VALUES (6, 6, 1);
 INSERT IGNORE INTO favorites (id, shop_id, user_id) VALUES (7, 7, 1);
 INSERT IGNORE INTO favorites (id, shop_id, user_id) VALUES (8, 8, 1);
 INSERT IGNORE INTO favorites (id, shop_id, user_id) VALUES (9, 9, 1);
 INSERT IGNORE INTO favorites (id, shop_id, user_id) VALUES (10, 10, 1);
 INSERT IGNORE INTO favorites (id, shop_id, user_id) VALUES (11, 11, 1);
 INSERT IGNORE INTO favorites (id, shop_id, user_id) VALUES (12, 12, 1);
