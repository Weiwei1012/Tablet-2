package com.cooking.merge.search

import android.content.ComponentCallbacks
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cooking.merge.R
import com.cooking.merge.adapters.FooditemsAdapter
import com.cooking.merge.adapters.OnFoodItemClickListener
import com.cooking.merge.food_fragment.FoodDetailsf
import com.cooking.merge.models.FooditemsModel
import java.util.*
import kotlin.collections.ArrayList

class SearchDetailsActivity : AppCompatActivity(), OnFoodItemClickListener {

    private lateinit var adapter: FooditemsAdapter
    private lateinit var details_rv: RecyclerView
    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var resultList: ArrayList<FooditemsModel>

    private val titles = arrayOf(
        "燕麥優格杯", "草莓蛋吐司", "培根蛋早餐燕麥粥", "全麥鮪魚蛋吐司", "法式吐司", "蔥花蛋捲",
        "帕瑪森花椰菜煎餅", "酪梨豆腐青醬吐司", "五分鐘咖哩優格鮮蔬三明治", "3分鐘微波法式吐司",
        "5分鐘舒芙蕾起司蛋", "太陽蛋吐司佐辣味焦糖奶油醬", "起士菇菇炒蛋盒子", "起司煎蛋", "蔥香味噌雞",
        "三杯菇菇雞", "安東燉雞", "醬燒雞腿杏鮑菇", "迷迭香黑胡椒馬鈴薯雞", "豉汁彩椒雞球", "洋蔥雞丁",
        "泰式涼拌柚香雞絲", "豆芽雞絲", "酥炸雞皮", "啤酒燒雞腿", "蔥燒雞腿", "香滷棒棒腿", "蜜汁雞翅",
        "蠔油雞翅", "美乃茄醬烤雞翅", "咖哩生薑燒雞腿", "咖哩優格烤雞翅", "青檸可樂雞翅腿", "甜辣醬烤雞翅",
        "咖哩烤雞翅腿", "黑胡椒檸檬醬烤雞翅腿", "起司嫩雞塊", "咖哩美乃滋嫩雞塊", "檸香鹽麴醬烤雞腿排",
        "嫩煎香料雞胸", "塔香味噌鹽麴烤雞腿排", "紅糟醬烤雞腿排", "檸檬醋", "蔬果綠拿鐵", "金黃芝麻地瓜",
        "水果小冰棒", "微笑香蕉蛋糕", "蜂蜜檸檬梅漬小番茄", "法式蘋果克拉芙緹", "巧克力夏威夷豆餅乾", "髒髒派",
        "迷你優格牛奶司康", "日式烏龍麵佐洋蔥燉雞", "XO醬炒泡麵", "酸辣泰式打拋豬拌炒泡麵", "絲瓜蛋麵線",
        "媽媽私房炒米粉", "肉絲炒麵", "雞絲燜米粉", "培根蛋奶義大利麵", "番茄雞蛋麵", "中日海陸烏龍乾麵",
        "家常肉末蛋米線", "冬日雨季小資男暖心湯麵", "越式涼拌米粉", "肉末冬粉", "照燒金針菇漢堡排", "蜂蜜醬燒五花肉",
        "蜂蜜芥末籽嫩煎豬排", "番茄燉肉", "韓式燒肉", "打拋豬", "櫛瓜炒肉末", "肉末蒸豆腐", "金針菇味噌肉燥",
        "古早味肉燥", "咖哩洋蔥炒肉片", "泰式風味炒肉片", "味噌野菜炒肉片", "薑汁豬肉", "泰式拌肉", "味噌炊飯",
        "和風白菜雞蛋丼", "鮭魚五穀米炒飯", "麻油菇菇雞飯", "空心菜梗炒飯", "海鮮泡飯", "豆腐飯", "日式豆腐丼",
        "泰式排骨湯泡飯", "沙茶豬肉燴飯", "高蛋白便當", "香芹皮芋粥", "麻婆豆腐", "醬燒嫩豆腐", "日式滑蛋豆腐",
        "可樂肉醬", "德式香腸炒洋芋佐芥末籽醬", "醬燒南瓜", "香料鹽洋芋", "馬鈴薯鮪魚煎餅", "蔬菜煎餅",
        "金針菇煎餅", "月見蔥爆豆芽菜蛋餅", "蔥油餅", "金沙春筍", "螞蟻上樹", "豆干肉絲", "紅燒豆干",
        "蔥蒜香炒豆皮", "醬燒豬肉豆皮捲", "肉末蒸蛋", "茄汁馬鈴薯", "奶油香煎馬鈴薯", "古早味碰皮白滷",
        "黑胡椒炒豆芽", "韓式雜菜", "咖哩炒三色", "醬油金針煮", "肉絲筍片", "櫻花蝦炒蘿蔔絲",
        "日式涼拌小黃瓜豆腐", "涼拌豆皮蔬菜", "涼拌干絲", "涼拌黑木耳", "涼拌菇菇醬", "菇菇蘿蔔雞湯",
        "金沙絲瓜湯", "海帶芽豆腐味噌湯", "鮮蔬豆腐味噌湯", "洋蔥湯", "麻油菇菇雞湯", "豚汁蔬菜湯",
        "蒜頭洋蔥雞湯", "韓式海帶芽排骨湯", "雞茸玉米羮"
    )
    private val images = arrayOf(
        R.drawable.breakfast1,
        R.drawable.breakfast2,
        R.drawable.breakfast11,
        R.drawable.breakfast7,
        R.drawable.breakfast8,
        R.drawable.breakfast6,
        R.drawable.breakfast9,
        R.drawable.breakfast10,
        R.drawable.breakfast12,
        R.drawable.breakfast13,
        R.drawable.breakfast14,
        R.drawable.breakfast15,
        R.drawable.breakfast5,
        R.drawable.breakfast4,
        R.drawable.ch1,
        R.drawable.ch21,
        R.drawable.ch23,
        R.drawable.ch2,
        R.drawable.ch3,
        R.drawable.ch8,
        R.drawable.ch25,
        R.drawable.ch24,
        R.drawable.ch6,
        R.drawable.ch5,
        R.drawable.ch4,
        R.drawable.ch13,
        R.drawable.ch17,
        R.drawable.ch9,
        R.drawable.ch7,
        R.drawable.ch10,
        R.drawable.ch11,
        R.drawable.ch12,
        R.drawable.ch14,
        R.drawable.ch22,
        R.drawable.ch18,
        R.drawable.ch15,
        R.drawable.ch19,
        R.drawable.ch20,
        R.drawable.ch16,
        R.drawable.ch26,
        R.drawable.ch27,
        R.drawable.ch28,
        R.drawable.dessert2,
        R.drawable.dessert3,
        R.drawable.dessert4,
        R.drawable.dessert5,
        R.drawable.dessert6,
        R.drawable.dessert7,
        R.drawable.dessert8,
        R.drawable.dessert9,
        R.drawable.dessert10,
        R.drawable.dessert11,
        R.drawable.noodles2,
        R.drawable.noodles3,
        R.drawable.noodles4,
        R.drawable.noodles5,
        R.drawable.noodles6,
        R.drawable.noodles7,
        R.drawable.noodles8,
        R.drawable.noodles9,
        R.drawable.noodles10,
        R.drawable.noodles11,
        R.drawable.noodles12,
        R.drawable.noodles13,
        R.drawable.noodles14,
        R.drawable.noodles15,
        R.drawable.pork13,
        R.drawable.pork6,
        R.drawable.pork8,
        R.drawable.pork11,
        R.drawable.pork12,
        R.drawable.pork9,
        R.drawable.pork1,
        R.drawable.pork2,
        R.drawable.pork10,
        R.drawable.pork3,
        R.drawable.pork4,
        R.drawable.pork5,
        R.drawable.pork7,
        R.drawable.pork14,
        R.drawable.pork15,
        R.drawable.rice4,
        R.drawable.rice5,
        R.drawable.rice6,
        R.drawable.rice7,
        R.drawable.rice8,
        R.drawable.rice9,
        R.drawable.rice10,
        R.drawable.rice11,
        R.drawable.rice12,
        R.drawable.rice13,
        R.drawable.rice15,
        R.drawable.rice17,
        R.drawable.sidedish1,
        R.drawable.sidedish8,
        R.drawable.sidedish27,
        R.drawable.sidedish2,
        R.drawable.sidedish7,
        R.drawable.sidedish10,
        R.drawable.sidedish11,
        R.drawable.sidedish22,
        R.drawable.sidedish23,
        R.drawable.sidedish24,
        R.drawable.sidedish25,
        R.drawable.sidedish12,
        R.drawable.sidedish13,
        R.drawable.sidedish16,
        R.drawable.sidedish17,
        R.drawable.sidedish18,
        R.drawable.sidedish15,
        R.drawable.sidedish19,
        R.drawable.sidedish20,
        R.drawable.sidedish14,
        R.drawable.sidedish29,
        R.drawable.sidedish31,
        R.drawable.sidedish32,
        R.drawable.sidedish33,
        R.drawable.sidedish26,
        R.drawable.sidedish21,
        R.drawable.sidedish3,
        R.drawable.sidedish5,
        R.drawable.sidedish28,
        R.drawable.sidedish30,
        R.drawable.sidedish6,
        R.drawable.sidedish9,
        R.drawable.sidedish4,
        R.drawable.soup1,
        R.drawable.soup2,
        R.drawable.soup3,
        R.drawable.soup4,
        R.drawable.soup5,
        R.drawable.soup6,
        R.drawable.soup7,
        R.drawable.soup8,
        R.drawable.soup9,
        R.drawable.soup10
    )
    private val ingredients = arrayOf(
        ("燕麥、優格、水果(可加可不加)"),
        ("草莓果醬、雞蛋"),
        ("雞蛋 1顆\n" + "洋蔥 1/2顆\n" + "培根 1條\n" + "即食大燕麥片 40g\n" + "高湯塊 1/4顆\n" + "清水 350ml\n" + "青蔥(裝飾用) 少許"),
        ("全麥吐司 4片\n" + "水煮鮪魚罐頭 1/2罐\n" + "煎蛋 2份\n" + "小黃瓜 適量"),
        ("吐司 3片\n" + "雞蛋 2顆\n" + "牛奶 100cc\n" + "奶油 2份\n" + "橄欖油 少許\n" + "蜂蜜 少許"),
        ("蔥 2株\n" + "太白粉 1/4茶匙\n" + "雞蛋 5顆"),
        ("<麵糊部分>\n" + "中筋麵粉 250公克\n" + "帕瑪森乳酪粉 226公克\n" + "鹽 4小匙\n" +
                "黑胡椒 4小匙\n" + "蛋 4顆\n" + "水 4大匙\n" +
                "\n<蔬菜部份>\n" + "花椰菜（白花菜）2棵\n" + "蒜 4瓣\n" + "巴西里 4小匙"),
        ("九層塔(剁碎) 1小株\n" + "酪梨 1/4顆\n" + "豆腐 1/4盒\n" + "大蒜泥 1瓣份"),
        ("吐司 2片\n" + "小黃瓜丁 1/2根份\n" + "牛蕃茄丁 1/2顆份\n" + "奶油 10g\n" + "芫荽籽(可略) 1小匙"),
        ("麵包 2~3片\n" + "牛奶 100ml\n" + "雞蛋 1顆\n" + "砂糖 1小匙\n" + "肉桂粉 1/2小匙\n" + "喜歡的水果 1大匙"),
        "蛋 1顆\n",
        "吐司或麵包片 1片\n" + "太陽蛋 1顆\n" + "無糖優格（或希臘優格） 1大匙",
        ("鴻禧菇 1包\n" + "雞蛋 2顆\n" + "起士片 2片\n" + "市售蛋餅皮 2片\n" + "牛奶 30g"),
        "起司 2片\n" + "鮮奶 適量\n" + "雞蛋 3顆\n" + "餡料可自選",
        "雞胸肉 約300克\n" + "洋蔥 1/4顆\n" + "青蔥 2枝",
        "去骨雞腿肉 2片\n" + "杏鮑菇 2根\n" + "鴻喜菇或雪白菇 1包\n" + "老薑 6片\n" + "蒜頭 6瓣\n" + "紅辣椒 半根\n" + "九層塔 1大把",
        "雞腿切塊 1斤\n" + "韓國寬冬粉 50克\n" + "青蔥 3根\n" + "洋蔥 半顆\n" + "紅蘿蔔 1根\n" + "馬鈴薯 1顆\n" + "薑末 0.5小匙\n" +
                "蒜末 3瓣\n" + "辣椒 適量\n" + "白芝麻 適量",
        "去骨雞腿肉 1支\n" + "杏鮑菇 3支\n" + "青蔥 1支\n" + "辣椒 1支",
        "雞腿切塊 4隻\n" + "馬鈴薯 6顆\n" + "蒜頭 3瓣\n" + "迷迭香 適量\n" + "橄欖油 1.5大匙",
        "去骨雞腿排 約350克\n" + "胡椒鹽 適量 \n" + "紅椒 1/4個\n" + "黃椒 1/4個\n" + "洋蔥 1/4個\n" + "蒜頭 3瓣\n" + "薑泥 1/2小匙",
        "雞胸肉 1份\n" + "洋蔥 半顆\n" + "老薑絲 適量\n" + "蔥段 適量",
        "雞胸肉 2片\n" + "小黃瓜 1根\n" + "柚子果肉 2-3瓣\n" + "小番茄 4-5顆",
        "雞胸肉 1片\n" + "綠豆芽 300克\n" + "炒菜油 2大匙\n",
        "雞皮 適量",
        "大雞腿 6支",
        "去骨雞腿排 約350克\n" + "洋蔥 半顆\n" + "紅甜椒 1/4顆\n" + "葱 3根\n" + "蒜頭 3瓣\n" + "胡椒鹽 適量",
        "雞棒棒腿 5支 \n" + "乾香菇 5小朵\n" + "青蔥 2根\n" + "油豆腐 3大塊\n" + "薑 3片",
        "雞中翅 18支",
        "雞中翅 14-15支\n" + "葱 3根 \n" + "薑 6片\n" + "醬油 1大匙\n" + "麻油 0.5大匙",
        "雞中翅 14支",
        "去骨雞腿排 2片\n" + "洋蔥 半顆",
        "雞中翅 12~16隻",
        "雞翅/翅腿 12支\n" + "青葱 1根\n" + "蒜頭 2瓣\n" + "可樂 150ml",
        "雞中翅 20隻\n" + "蒜泥 3瓣",
        "雞翅/雞腿 12支",
        "雞中翅/翅小腿 10-12支",
        "雞胸肉 2片",
        "雞胸肉 1片",
        "去骨雞腿排 1片",
        "雞胸 2~3片\n" + "開水 200ml",
        "去骨雞腿排 2片",
        "去骨雞腿排 1片 \n" + "耐高溫植物油 適量",
        "檸檬 1斤",
        "蔬菜 適量\n" + "水果 適量\n" + "堅果(可有可無) 1小匙\n" + "開水 適量",
        "地瓜 1顆",
        "水果 適量\n" + "蘋果汁/乳酸飲料 適量\n" + "短冰棒棍/水果叉 適量",
        "<香蕉麵糊>\n" + "熟香蕉 2大根\n" + "蛋 1顆\n" + "蔗糖/紅糖 150克\n" + "無鹽奶油 110克\n" + "希臘式優格(無糖為佳) 60克\n" +
                "香草精 1小匙\n" + "低筋麵粉 125克\n" + "小蘇打粉 1小匙\n" + "鹽 1小撮",
        "小番茄 300克\n" + "酸梅 10~15顆\n" + "熱開水 100ml\n" + "蜂蜜 5大匙\n" + "新鮮檸檬汁 1顆",
        "大蘋果 1顆\n" + "奶油 10克\n" + "低筋麵粉 20克\n" + "蛋 1顆\n" + "牛奶 90克\n" + "糖粉(裝飾用) 適量\n" + "薄荷葉(裝飾用) 兩片\n",
        "無鹽奶油 70克\n" + "黑糖粉 55克\n" + "香草精 1/2小匙\n" + "蛋黃 1顆\n" + "中筋麵粉 110克\n" + "蘇打粉 1/4小匙\n" +
                "巧克力豆 50克\n" + "無調味夏威夷豆 50克",
        "冷凍起酥皮 3片\n" + "巧克力 50克",
        "無鹽奶油 60克\n" + "低筋麵粉 225克\n" + "全麥麵粉 25克\n" +
                "泡打粉 8克\n" + "原味優格 70克\n" + "鮮奶 30克\n" + "雞蛋 1顆 \n" + "蛋黃 1顆\n" + "蔓越莓乾/葡萄乾(可略) 50克\n" + "高筋麵粉 適量",
        "洋蔥 1/2切片\n" + "昆布 2條(或海帶芽一碗) \n" + "柴魚粉 1.5匙(或柴魚片一碗)\n" + "烏龍麵 1包\n" + "雞胸 1片\n",
        "泡麵 2包\n" + "蝦仁 6隻\n" + "洋蔥 1/4顆\n" + "韭黃 30克\n" + "雞蛋 1顆\n" + "豆芽菜 30克\n" + "蔥 30克",
        "豬絞肉 150g\n" + "蒜仁(去膜切片) 1瓣\n" + "紅辣椒(去蒂頭切段) 1根\n" + "小蕃茄(切半) 5顆\n" + "九層塔 1手把\n" + "泡麵(事先泡軟) 1片\n" + "雞蛋(不要打散) 1顆",
        "絲瓜 1條\n" + "蝦米 6~8隻\n" + "蔥 3根\n" + "白麵線 2束\n" + "蛋 2顆",
        "米粉 150g\n" + "蝦米 30g\n" + "香菇 6朵\n" + "高麗菜 1/8顆",
        "油麵 1200g\n" + "豬肉絲 150g\n" + "紅蘿蔔 60g\n" + "青蔥 1根\n" + "高麗菜 250g\n" + "洋蔥 100g\n" + "水 450c.c",
        "米粉 100g\n" + "鹽烤雞胸肉 適量\n" + "乾香菇/蝦米 適量\n" + "高麗菜 適量\n" + "紅蘿蔔 適量\n" + "生辣椒 少許",
        "培根 2片\n" + "蛋 2顆\n" + "鮮奶油 3大匙\n" + "義大利麵 1/4包",
        "牛番茄 1顆\n" + "蛋 2顆\n" + "蒜頭 1瓣\n" + "青蔥 1根\n" + "拉麵 約115g",
        "烏龍麵 依個人食量\n" + "鮮蝦 4~5隻\n" + "豬肉肉片 約3~5片\n" + "青菜 少許",
        "越南澱粉條(細) 80克\n" + "紅蘿蔔 1小塊\n" + "黑木耳 1片\n" + "鴻喜菇 半包\n" + "貢丸 兩顆\n" + "青菜 1株\n" + "雞高湯 500ml\n" + "雞蛋 1顆 ",
        "烏龍麵 依個人食量\n" + "紅蘿蔔 1/2根\n" + "地瓜 1/3顆\n" + "牛肉 150g\n" + "白蘿蔔 1/4根\n" + "芹菜(去腥味用) 些許",
        "越南澱粉條(細) 160克\n" + "紅蘿蔔 1/3條\n" + "小黃瓜 1條\n" + "去皮花生 2大匙\n" + "***雞腿排與肉片擇一即可***\n" + "去骨雞腿排 2片\n" +
                "牛/豬肉片 200克",
        "豬絞肉 150g\n" + "冬粉 3把\n" + "青蔥 2根\n" + "香菜(可有可無) 適量\n" + "蒜頭 2瓣\n" + "辣椒 0.5~1條",
        "豬絞肉 300克\n" + "金針菇 1包\n" + "蛋 1顆\n" + "葱末 1根\n" + "薑泥 1小塊",
        "五花肉 1條\n" + "蔥 1根\n" + "薑 3片\n" + "米酒 2大匙",
        "全聯豬里肌厚排 2片",
        "豬肉 600~800克\n" + "(胛心、梅花、豬腱皆可)\n" + "中型洋蔥 1顆\n" + "中型番茄 2顆\n" + "薑末 3片\n" + "蒜末 5瓣",
        "火鍋肉片(牛豬皆可) 200克\n" + "白芝麻(可略) 適量",
        "豬絞肉 300克\n" + "洋蔥 半顆\n" + "蒜末 2瓣\n" + "小番茄 10顆\n" + "九層塔 1把",
        "櫛瓜 1小條\n" + "細絞肉 120g\n" + "紅蘿蔔 1/4條\n" + "大蒜 1粒\n" + "醬油 1.5匙\n" + "糖 1/2匙\n" + "鹽 少許",
        "嫩豆腐 1盒\n" + "豬絞肉 200g\n" + "鮮香菇 2朵\n" + "蒜頭 1瓣",
        "豬絞肉 150g\n" + "金針菇 1/2小包\n" + "蔥 半支",
        "紅葱頭末 4大匙\n" + "中大型乾香菇 4-5朵\n" + "絞肉(225-300克\n" + "炒菜油 3大匙",
        "豬肉薄片(五花/梅花皆可) 200克\n" + "洋蔥 半顆\n" + "甜豆 6根\n" + "開水 50ml",
        "五花肉薄片 200克\n" + "洋蔥 半顆\n" + "紅甜椒 1/4顆\n" + "香菜 3大株",
        "豬肉薄片 100克\n" + "鴻禧菇 半包\n" + "高麗菜 3大片\n" + "紅蘿蔔 一小段",
        "梅花豬肉片(薄片) 200克\n" + "洋蔥絲 半顆\n" + "太白粉 1小匙\n" + "開水 1大匙",
        "松坂肉 2片\n" + "小黃瓜 1~2條",
        "白米 0.5杯\n" + "雪白菇 0.5包\n" + "紅蘿蔔 0.25條 \n" + "板豆腐 0.25盒\n" + "水 0.5杯",
        "大白菜 5片\n" + "大蒜 2辦\n" + "雞蛋 1顆\n" + "柴魚片 2大匙\n",
        "鮭魚 半片 \n" + "毛豆 碗裝約7分滿\n" + "熟五穀米飯(冷飯) 2碗",
        "米 1.5米杯\n" + "薑片 10片\n" + "蒜末 1小匙\n" + "乾香菇 3朵\n" + "各種菇類(任意) 適量\n" + "三色蔬菜 1小碗\n" + "雞胸肉/去骨雞腿 1片",
        "空心菜梗 1把\n" + "雞蛋 1顆\n" + "蝦仁 3~4隻\n" + "豬絞肉 適量\n" + "冷飯 一人份\n" + "冷凍蔬菜 適量",
        "蝦仁 75克\n" + "透抽 75克\n" + "青葱 1根\n" + "紅蘿蔔 75克\n" + "蘑菇/鴻喜菇 75克\n" + "大白菜 150克\n" + "芹菜 1根\n"
                + "太白粉(洗蝦仁用) 1大匙\n" + "水 600ml\n" + "白飯 2碗",
        "豆腐 1盒\n" + "白飯 1碗",
        "家常/火鍋豆腐 1盒\n" + "洋蔥 1/4顆\n" + "大白菜葉 2片\n" + "雞蛋 2顆\n" + "葱花 適量",
        "排骨 1.5斤\n" + "杏鮑菇 3~4根\n" + "薑 3片",
        "梅花豬肉片 200克\n" + "洋蔥 1/4顆\n" + "蒜頭 1瓣\n" + "空心菜 半把\n" + "紅辣椒(可略) 半根",
        "板豆腐 2塊\n" + "新鮮玉米 2根\n" + "藜麥 2杯\n" + "毛豆 200克\n" + "紅蘿蔔 1根\n" + "花椰菜 1顆\n" + "洋蔥 1顆\n" + "薑絲 少許",
        "香菇 4~5朵\n" + "芋頭 1/4顆\n" + "西洋芹 3片\n" + "肉燥 少許\n" + "白飯 適量\n" + "皮蛋 2顆\n" + "熱水 3碗",
        "嫩豆腐 1盒\n" + "細絞肉 適量\n" + "蔥 1根\n" + "蒜頭 2粒",
        "豆腐 1盒\n" + "青蔥 1根\n" + "嫩薑 3片",
        "豆腐 1盒\n" + "雞蛋 3顆\n" + "洋蔥 1/8顆\n" + "鮮香菇 2朵\n" + "蔥 1根",
        "豬絞肉 300克\n" + "紅蔥頭 5瓣\n" + "蒜頭 5瓣\n" + "薑 1小塊\n" + "八角 1顆",
        "馬鈴薯 1顆\n" + "培根 2片\n" + "德式香腸 2條",
        "南瓜 約350克\n" + "薑 3片",
        "小型馬鈴薯 2~4顆\n" + "橄欖油 適量",
        "馬鈴薯 1顆\n" + "鮪魚罐頭 1份\n" + "雞蛋 2顆\n" + "蔥末 半支",
        "芹菜葉 50g\n" + "高麗菜 100g\n" + "紅蘿蔔 25g\n" + "蛋 1顆\n" + "低筋麵粉 1碗\n" + "水 1碗",
        "金針菇 200g\n" + "雞蛋 2個\n" + "中筋麵粉 30g\n" + "紅蘿蔔(切絲) 半條\n" + "青蔥 2根",
        "蛋餅皮 1片\n" + "豆芽菜 100g\n" + "大蒜 2瓣\n" + "青蔥 適量\n" + "雞蛋 1顆",
        "中筋麵粉 3杯\n" + "熱開水 1杯\n" + "冷開水 1/3杯\n" + "豬油 4大匙\n" + "葱末 適量",
        "春筍 1支\n" + "鹹鴨蛋 3個\n" + "蔥 2支\n" + "蒜末 2大匙",
        "冬粉 2把\n" + "豬絞肉 200g\n" + "蔥 2支\n" + "薑 15g\n" + "蒜 4瓣\n" + "大辣椒 1支",
        "五香豆干 10片\n" + "豬肉絲 150克\n" + "葱 2根\n" + "蒜頭 1瓣\n" + "辣椒絲 適量",
        "原味豆干 8個\n" + "大蒜片 1個\n" + "辣椒末 1根",
        "新鮮豆皮 6片\n" + "青蔥 3根\n" + "蒜頭 4瓣",
        "低脂瘦絞肉 400g\n" + "豆腐 1盒\n" + "嫩豆皮 5捲\n" + "老薑 15g",
        "蛋 2顆\n" + "絞肉 100g\n" + "薑末 1/4小匙\n" + "蔥花 1小把",
        "馬鈴薯 2顆",
        "馬鈴薯 2顆\n" + "奶油 20g",
        "白菜 2顆\n" + "蝦米 適量\n" + "炸豬皮 適量\n" + "豬骨 600克\n" + "蒜末 1小匙",
        "綠豆芽 1包\n" + "韭菜 適量\n" + "蒜頭 適量",
        "蒜泥 2瓣\n" + "菠菜(切段) 1把\n" + "洋蔥(切絲) 1/2顆\n" + "香菇(去蒂切絲) 5朵\n" + "黑木耳(切絲) 1-2張\n" + "中型紅蘿蔔(切絲) 1/3根\n" +
                "豬後腿肉絲 200-250g\n" + "韓式芝麻油 1小匙\n" + "韓式冬粉 250g\n" + "白芝麻 1大匙\n" + "醬油 3.5大匙\n" + "白胡椒 適量\n" +
                "蠔油 1/2大匙\n" + "細砂糖 2小匙\n",
        "毛豆仁 半碗\n" + "紅蘿蔔丁 半碗\n" + "玉米粒 半碗",
        "金針菇 2包",
        "綠竹筍 2支\n" + "豬肉絲 30克\n" + "蔥 1根\n" + "水 400ml",
        "白蘿蔔 半條\n" + "香菜 1~2株\n" + "櫻花蝦 1小把",
        "豆腐 1盒\n" + "蒜末 1大匙\n" + "小黃瓜 1/2條",
        "豆皮片 1包\n" + "小黃瓜 1條\n" + "紅蘿蔔 40g\n" + "黑木耳 140g\n" + "香菜 適量\n" + "蔥 2根",
        "干絲 300克\n" + "芹菜 2根\n" + "紅蘿蔔 1/4根",
        "黑木耳 200g\n" + "鳳梨 1圓片\n" + "嫩薑 5~8薄片\n" + "香菜 1大株\n" + "紅辣椒(可有可無) 適量",
        "金針菇(洗淨切小段) 1包\n" + "柴魚片 些許",
        "雞胸肉 適量\n" + "金針菇 或任一菇類\n" + "蘿蔔 適量\n" + "黑菜脯(可加可不加) 適量",
        "絲瓜 1條\n" + "薑 切絲\n" + "鹹蛋 1顆",
        "中華雞蛋豆腐 1盒\n" + "海帶芽 1把\n" + "柴魚片 1把\n" + "蔥 3根",
        "板豆腐 1盒\n" + "高麗菜絲 150g\n" + "洋蔥絲 100g\n" + "青蔥絲 3根\n" + "柴魚片 50g\n",
        "洋蔥 2/3顆\n" + "雞蛋 1個\n" + "生薑 適量\n" + "大蒜 2片\n" + "太白粉(適量) 勾芡",
        "土雞腿切塊 600克\n" + "麻油 2-3大匙\n" + "老薑切片 12片\n" + "金針菇 1包\n" + "鴻喜菇 1包\n" + "枸杞 1把\n" + "高麗菜 適量\n" + "米酒 100ml\n" + "水 2公升",
        "豬五花薄片 200克\n" + "洋蔥 半顆\n" + "高麗菜 1/8顆\n" + "鴻喜菇 半包\n" + "金針菇 半包\n" + "紅蘿蔔 30克\n" + "白蘿蔔 50克\n" + "青葱 1根\n" + "信州味噌 3大匙\n" + "熱開水 1000ml",
        "仿土雞腿 1大支\n" + "薑 3片\n" + "中型洋蔥 1顆\n" + "蒜頭 12瓣\n" + "娃娃菜 2株",
        "排骨肉 1~1.5斤\n" + "韓國海帶芽 30克",
        "雞骨架 1付\n" + "玉米粉(太白粉亦可) 1大匙\n" + "開水 50ml\n" + "玉米醬 1罐\n" + "蛋 1顆\n" + "鹽 適量\n" + "火腿(切末) 1片"
    )
    private val sauces = arrayOf(
        "不須調味料",
        "不須調味料",
        "鹽 適量\n" + "黑胡椒 適量",
        "美乃滋 適量",
        "不須調味料",
        "鹽巴 1/2茶匙 \n" + "白胡椒粉 1/4茶匙",
        "不須額外的調味料",
        "鹽 少許\n" + "黑胡椒 少許\n" + "無調味堅果(壓碎) 3顆",
        "<咖哩優格醬>\n" + "希臘式優格 2大匙\n" + "小Ｏ坊純素咖哩粉 1/2小匙\n" + "大蒜粉 1/4小匙\n" + "鹽 1/4小匙\n" + "黑胡椒 1/4小匙",
        "不需要調味料",
        "起司粉 1大匙\n" + "黑胡椒粉 1小撮",
        "<辣味焦糖奶油醬>\n" + "蜂蜜或糖 1小匙\n" + "奶油 10g\n" + "乾辣椒片 1/4小匙\n" + "白醋 1/2小匙\n",
        ("鹽 適量\n" + "黑胡椒 適量"),
        ("不須調味料"),
        "味噌 2匙\n" + "醬油 1匙\n" + "白胡椒粉 少許\n",
        "<調味料> \n" + "麻油 1大匙\n" + "醬油膏 2.5大匙\n" + "米酒 2大匙" + "\n <醃料>\n" + "胡椒鹽 適量\n" + "米酒 1小匙 \n",
        "黑麻油 1.5大匙\n" + "水 500ml\n" + "料理米酒 2大匙\n" + "蠔油 1大匙\n" + "醬油 4大匙\n" + "蜂蜜 1大匙\n",
        "<調味料> \n" + "素蠔油 1大匙\n" + "味醂 1小匙\n" + "\n <醃料>\n" + "素蠔油 1大匙 \n" + "低鹽醬油 1大匙\n" + "米酒 1大匙\n" + "砂糖 1小匙\n",
        "香菇素蠔油 2大匙\n" + "米酒 1大匙\n" + "砂糖 2匙\n" + "黑胡椒 1大匙\n" + "水 300~500c.c\n",
        "豆豉醬 1大匙\n" + "糖 1小匙\n" + "米酒 2大匙\n" + "蠔油 2小匙\n",
        "<調味料>\n" + "醬油 適量\n" + "油膏 適量\n" + "砂糖 適量\n" + "黑胡椒粒 適量\n" + "水 適量\n" + "\n<醃料>\n" + "醬油 少許\n" +
                "米酒 少許 \n" + "砂糖 少許\n" + "胡椒粉 少許\n" + "太白粉 兩匙\n",
        "<雞胸醃料>\n" + "鹽 1小匙\n" + "開水 200ml\n" + "\n <泰式醬料>\n" + "新鮮檸檬汁 半顆 \n" + "糖 2小匙\n" + "魚露 1大匙\n" + "蒜泥 1瓣\n" + "香菜碎 1株\n",
        "醬油 20c.c\n" + "味琳 20c.c\n" + "水 20c.c\n" + "白芝麻 少許",
        "鹽 適量\n" + "七味粉 適量\n",
        "醬油 50ml\n" + "二砂糖 30g\n" + "罐裝啤酒 1罐",
        "米酒 1大匙\n" + "蠔油 1大匙\n" + "番茄醬 0.5大匙\n" + "糖 1小撮\n",
        "醬油 4大匙\n" + "蠔油 2大匙\n" + "米酒 2大匙\n" + "糖 1小匙\n" + "五香粉 1/4小匙\n" + "八角 1個 \n" + "水 500ml\n",
        "蜂蜜 1.5大匙\n" + "蠔油 2大匙\n" + "醬油 1大匙\n" + "蒜頭 3瓣\n" + "研磨黑胡椒 轉15下\n",
        "蠔油 1大匙\n" + "米酒 0.5大匙\n" + "水 200ml\n" + "糖 0.5小匙\n" + "胡椒粉 1/4小匙\n",
        "<調味料>" + "胡椒鹽 適量\n" + "\n<醃料>\n" + "美乃滋 3大匙 \n" + "番茄醬 1.5大匙\n" + "研磨黑胡椒 適量\n" + "義大利香料 適量\n" + "蒜泥 2瓣\n",
        "<調味料>\n" + "米酒 1大匙 \n" + "醬油 1大匙\n" + "味醂 1大匙\n" + "番茄醬 1/2小匙\n" + "薑泥 2小匙\n" + "\n<醃料> \n" + "白胡椒鹽 適量\n" + "咖哩粉 1大匙\n",
        "<調味料>\n" + "白胡椒鹽 適量\n" + "\n<醃料>\n" + "咖哩粉 1大匙\n" + "番茄醬 2大匙\n" + "希臘式優格(有無糖皆可) 3大匙\n" + "蒜泥 2瓣\n" + "鹽 1/4小匙\n",
        "白胡椒鹽 適量\n" + "醬油 1大匙\n" + "新鮮檸檬汁 1-2小匙",
        "甜辣醬 4大匙\n" + "蜂蜜 1大匙\n" + "研磨黑胡椒 適量\n" + "義式香料 適量\n",
        "鹽 1/4小匙\n" + "咖哩粉 2小匙\n" + "薑黃粉2小匙\n" + "孜然粉 1小匙\n" + "米酒 2大匙\n",
        "醬油 1.5大匙\n" + "蒜末 1瓣\n" + "耐高溫植物油 1大匙\n" + "檸檬汁 1/2大匙\n" + "研磨黑胡椒 1/2小匙\n" + "鹽 1/8小匙\n",
        "鹽 適量\n" + "研磨黑胡椒 適量\n" + "蛋 1顆\n" + "起司粉 3大匙\n" + "麵包粉 1大匙\n" + "玉米粉(或太白粉) 2小匙\n" + "香草鹽 適量\n",
        "米酒 1小匙\n" + "白胡椒鹽 適量\n" + "咖哩粉 1小匙\n" + "起司粉 1小匙\n" + "美乃滋 1/2大匙\n" + "義式香料 適量\n" + "玉米粉(可略) 1小匙\n",
        "<調味料>\n" + "耐高溫植物油 適量\n" + "\n<醃料> \n" + "鹽麴 1大匙\n" + "蒜泥 1瓣\n" + "研磨黑胡椒 適量\n" + "檸檬汁 1大匙\n" + "醬油 1小匙\n",
        "鹽 1小匙\n" + "研磨黑胡椒 適量\n" + "咖哩粉 適量\n" + "薑黃粉(可有可無) 適量",
        "九層塔 1把\n" + "信州味噌 2大匙\n" + "鹽麴 1大匙\n" + "味醂 1大匙\n" +
                "米酒 1大匙\n" + "醬油 2小匙\n" + "糖 2小匙\n",
        "紅糟醬 1大匙\n" + "味醂 1大匙\n" + "醬油 1小匙\n" + "蒜泥 1瓣\n",
        "冰糖 150公克\n" + "高粱醋 1斤",
        "不須調味料",
        "黑芝麻 1匙\n" + "鹽巴 少許\n" + "蜂蜜 適量",
        "不須調味料",
        "<奶油乳酪餡> \n" + "Cream Cheese 110克\n" + "蛋 1顆\n" + "白砂糖 50克\n" + "低筋麵粉 3大匙",
        "不須調味料",
        "三溫糖/紅糖/蔗糖 10克" + "香草糖/白砂糖/上白糖 20克",
        "白糖 45克\n" + "鹽 1/4小匙\n",
        "可可粉 適量",
        "白砂糖 40克\n" + "鹽 4克 \n",
        "日式醬油 4湯匙\n\n",
        "白胡椒粉 適量\n" + "蠔油 5克 \n" + "糖 2克\n" + "香油 5克\n" + "鹽 2克\n" + "太白粉 2克\n" + "XO醬 15克\n" + "水 100克\n" + "醬油 5克\n",
        "魚露 1茶匙\n" + "細砂糖 1茶匙\n" + "檸檬汁 1匙\n" + "醬油 1大匙\n" + "蠔油 1匙\n" + "高湯 2大匙\n",
        "鹽巴 適量",
        "醬油膏 3大匙\n" + "黑胡椒粉 適量",
        "<調味料> \n" + "醬油 3.5大匙\n" + "烏醋 3大匙\n" + "糖 2/3茶匙\n" + "白胡椒粉 少許\n" +
                "鹽 適量" + "<醃料> \n" + "醬油 1/2茶匙\n" + "白胡椒粉 少許\n" + "太白粉 1/2茶匙\n" + "米酒 1茶匙\n",
        "油蔥酥 少許\n" + "白芝麻油 少許\n" + "白胡椒粉 少許\n" + "水/高湯 180c.c\n",
        "粗黑胡椒粉 少許\n" + "鹽 少許\n" + "起司粉 適量\n",
        "白胡椒粉 1/16茶匙\n" + "鹽 1/4茶匙\n" + "醬油 2/3大匙\n" + "香油 1茶匙\n" + "番茄醬 1.5大匙\n" + "糖 1/3茶匙\n",
        "老干媽 1匙\n" + "冰塊 約一把\n",
        "白醋 1小匙 \n" + "鹽 適量\n" + "胡椒 適量\n",
        "鹽巴 適量\n",
        "<醃料>\n" + "糖 3大匙\n" + "紅辣椒碎 適量\n" + "新鮮檸檬汁 1顆\n" + "魚露 1.5大匙\n" + "開水 60ml\n",
        "醬油 1大匙\n" + "素蠔油/醬油膏 1大匙\n" + "糖 1/4茶匙\n" + "胡椒粉 適量\n" + "水 3米杯\n",
        "<調味料> \n" + "醬油 0.5大匙\n" + "麻油 1大匙\n" + "白胡椒粉 適量\n" + "\n<醃料>  \n" + "料理米酒 1大匙\n" + "醬油 1大匙\n" + "味醂 1大匙\n" + "糖 1小匙\n",
        "醬油 1~1.5大匙\n" + "蠔油 1大匙\n" + "蜂蜜 2小匙\n" + "水 50ml\n",
        "<調味料> \n" + "黑胡椒 適量\n" + "玫瑰鹽 適量\n" + "\n<蜂蜜芥末醬> \n" + "顆粒芥末籽醬 2小匙\n" + "醬油 1/2小匙\n" + "蜂蜜 2小匙\n" + "米酒 2大匙\n",
        "米酒 2大匙\n" + "醬油 3大匙\n" + "番茄醬 2大匙\n" + "月桂葉 2片\n" + "水 450~500ml\n" + "糖 適量\n",
        "中型蘋果或水梨 1/4個\n" + "米酒 1大匙\n" + "糖 1小匙\n" + "醬油 1大匙\n" + "麻油 1/2小匙\n" + "蒜泥 1瓣\n",
        "米酒 1大匙\n" + "蠔油 1大匙\n" + "醬油 0.5大匙\n" + "魚露 1/4-1/2小匙\n",
        "醬油 1茶匙\n" + "白胡椒 1白胡椒1小匙\n" + "米酒 1大匙\n" + "鹽 少許\n" + "白麻油 1小匙\n",
        "米酒 2匙\n" + "醬油 2匙\n" + "胡椒粉 適量\n" + "鹽 1/2茶匙\n",
        "米酒 1大匙\n" + "砂糖 1~1.5小匙\n" + "味噌 1大匙\n" + "豆瓣醬 (可略) 1小匙\n" + "醬油 2小匙\n" + "太白粉 (可略) 1小匙\n" + "水 70ml\n",
        "醬油 4大匙\n" + "糖 2小匙\n" + "麻油 2小匙\n" + "泡香菇水 250ml\n",
        "鹽 適量",
        "不需要調味料",
        "<調味料> \n" + "炒菜油 1小匙\n" + "麻油 1小匙\n" + "\n <味噌醬汁> \n" + "信州味噌 1大匙\n" + "米酒 1大匙\n" + "薑泥 1/2小匙\n" + "味醂 1大匙\n" + "醬油膏 1/2-1小匙\n" + "水 1大匙\n",
        "米酒 2大匙\n" + "本味醂 1大匙\n" + "醬油 2大匙\n" + "嫩薑泥 2大匙\n" + "西洋梨/水梨 (磨泥) 40克\n",
        "<泰式醬汁> \n" + "魚露 2大匙\n" + "新鮮檸檬汁 2大匙\n" + "蒜末 2瓣\n" + "紅辣椒末(可略) 適量\n" + "糖 1.5大匙\n" +
                "\n <醃料> \n" + "蒜頭碎(2瓣\n" + "醬油 1.5大匙\n" + "糖 1小匙\n" + "白胡椒粉 適量\n",
        "味噌(亦可用味噌湯包) 10克\n",
        "鰹魚露/日式醬油 1大匙\n" + "鹽巴 少許 \n" + "味精 少許\n" + "七味粉(可有可無)\n",
        "海鹽 適量\n" + "黑胡椒 適量\n",
        "麻油 2小匙\n" + "米酒 1小匙\n" + "白胡椒粉 少許\n",
        "淡色醬油 10ml\n" + "鹽 適量\n",
        "白胡椒粉 1/4匙\n" + "鹽 0.5~1匙\n",
        "不須調味料\n",
        "醬油 2大匙\n" + "味醂 1/2大匙\n" + "烹大師 1/2小匙\n" + "糖 1/2小匙\n" + "水 120ml\n",
        "蠔油 2大匙\n" + "米酒 2大匙\n" + "白醬油 1大匙\n" + "大蒜粉 3大匙\n" + "白胡椒粉 1/2小匙\n" + "水 1600ml\n" + "鹽 適量\n",
        "白胡椒 適量\n" + "薑黃粉 適量\n" + "鹽 適量\n",
        "黑胡椒 適量\n" + "醬油 適量\n" + "薑黃粉 適量\n" + "油 適量\n" + "鹽巴 適量\n" + "花生醬(無糖佳) 適量\n ",
        "鹽 少許\n" + "胡椒粉 適量\n" + "香油 少許\n",
        "辣豆瓣醬2 匙\n" + "醬油 3匙\n" + "味醂 1匙\n" + "白胡椒粉 少許\n" + "水 適量\n" + "香油 1湯匙\n" + "太白粉水 適量\n",
        "醬油 1大匙\n" + "豆瓣醬 1大匙\n" + "糖 1小匙\n" + "水 120ml\n" + "太白粉水 1/2匙\n" + "香油 適量\n",
        "油 1茶匙\n" + "七味粉 適量\n" + "日式昆布醬油 70ml\n" + "味醂 1又1/2匙\n" + "糖 1/2匙\n" + "水 180ml\n",
        "醬油膏 1大匙\n" + "米酒 2大匙\n" + "鹽 1/2小匙\n" + "醬油 1大匙\n" + "冰糖 1小匙\n" + "可樂 170ml\n" + "水 350ml\n",
        "蒜頭碎 1瓣\n" + "芥末籽醬 1小匙\n" + "研磨黑胡椒 適量\n" + "鹽 適量\n" + "巴西利末(可略) 適量\n" + "橄欖油 適量\n",
        "白醬油/日式醬油 1大匙\n" + "味醂 1大匙\n" + "水 50ml\n",
        "研磨黑胡椒 適量\n" + "研磨鹽 適量\n" + "各式乾燥香草 適量\n" + "紅椒粉 適量\n" + "起士粉 適量\n",
        "鹽 適量\n" + "黑胡椒 適量\n",
        "鹽 少許\n" + "白胡椒粉 少許\n",
        "鹽 少許\n" + "黑胡椒粉 少許\n" + "麻油 少許\n",
        "鹽 適量\n" + "黑胡椒 適量\n" + "醬油 適量\n",
        "鹽 2小匙\n",
        "糖 半匙\n" + "油 2大匙\n" + "米酒 2大匙\n",
        "豆瓣醬 1湯匙\n" + "醬油 1湯匙\n" + "糖 1匙\n" + "白胡椒 1匙\n" + "蔬菜高湯/食用水 適量\n",
        "<調味料> \n" + "白醬油 1.5匙\n" + "胡椒鹽 適量\n" + "香油 1小匙\n" + "\n<醃料> \n" + "米酒 1大匙\n" +
                "醬油 1大匙\n" + "糖 1/4小匙\n" + "白胡椒粉 適量\n" + "太白粉 1小匙\n" + "油(下鍋前加) 適量\n",
        "冰糖 0.5小匙\n" + "鹽 0.5小匙\n" + "醬油 1大匙\n" + "白醋 1小匙\n",
        "胡椒鹽 適量\n" + "鹽 1小匙\n",
        "鹽 1/2小匙\n" + "白胡椒粉 1/4小匙\n" + "醬油 1大匙\n" + "味醂 1/2大匙\n" + "水 3大匙\n",
        "麻油/香油 1小匙\n" + "白胡椒粉 少許\n" + "醬油 3大匙\n" + "糖1 /2小匙\n" + "水 1.5米杯\n",
        "番茄醬 2匙\n" + "義大利香料 少許",
        "鹽 1/2小匙\n" + "黑胡椒 1小匙\n" + "起士粉 1小匙\n",
        "鹽巴 少許\n" + "香油 適量\n" + "水 800c.c\n",
        "鹽巴 適量\n" + "黑胡椒 適量\n" + "鰹魚粉/昆布粉 適量\n",
        "醬油 1小匙\n" + "白胡椒粉 適量\n" + "韓式芝麻油 少許\n",
        "咖哩塊 1/2大匙\n" + "鹽 少許\n" + "黑胡椒 少許\n",
        "味醂 100c.c\n" + "日式柴魚醬油 100c.c",
        "鹽 1小匙\n" + "醬油膏 1小匙\n" + "米酒 1小匙\n" + "太白粉(可略) 1/2小匙\n" + "炒菜油 1小匙\n",
        "<調味料> \n" + "烹大師(可略) 適量\n" + "鮮味炒手(可略) 適量\n" + "鹽 適量\n" + "\n<醃料> \n" +
                "鹽(1/4小匙) ",
        "蘋果泥 2大匙\n" + "日式干貝醬油 2大匙\n" + "七味粉 1/2茶匙\n" + "蘋果醋 1大匙\n" + "糖 1茶匙\n" + "香油 少許\n" + "熟白芝麻 1/2茶匙\n",
        "鹽巴 少許\n" + "香油 適量\n" + "醬油膏 適量\n" + "胡椒粉 少許",
        "醬油膏 1大匙\n" + "白胡椒鹽 少許\n" + "蒜末 2瓣\n" + "鮮雞晶 1小匙\n" + "鹽 1小匙\n" + "香油 1大匙\n",
        "糖 1大匙\n" + "新鮮檸檬汁 1大匙\n" + "醬油膏 2大匙\n" + "香油 1大匙\n",
        "醬油 30c.c.\n" + "味霖 30c.c.\n" + "水 500c.c.\n" + "鹽巴 0.5匙\n",
        "鹽巴 適量\n",
        "鹽巴 適量\n",
        "味噌 適量\n",
        "味噌 2大匙\n",
        "味醂 1小匙\n" + "醬油 1大匙\n" + "麻油 1小匙",
        "雞粉(鮮雞晶) 1大匙\n" + "鹽 適量\n",
        "味醂 1大匙\n" + "烹大師 1小匙\n",
        "鹽 適量\n" + "白胡椒粉 適量\n",
        "鹽 適量\n" + "黑麻油 1.5大匙\n" + "醬油 2大匙\n" + "蒜泥 1瓣\n" + "水 1.5~2公升\n",
        "葱(切段) 1根\n" + "薑 3片\n" + "水 1200ml"
    )
    private val links = arrayOf(
        "http://blog.morningshop.tw/post/healthy-breakfast",
        "http://blog.morningshop.tw/post/healthy-breakfast",
        "https://icook.tw/recipes/313622",
        "https://icook.tw/recipes/339624",
        "https://icook.tw/recipes/334694",
        "https://icook.tw/recipes/340861",
        "https://icook.tw/recipes/330340",
        "https://icook.tw/recipes/322587",
        "https://icook.tw/recipes/299252",
        "https://icook.tw/recipes/278519",
        "https://icook.tw/recipes/269552",
        "https://icook.tw/recipes/269552",
        "https://icook.tw/recipes/308198",
        "https://icook.tw/recipes/340891",
        "https://icook.tw/recipes/342555",
        "https://icook.tw/recipes/270334",
        "https://icook.tw/recipes/242709",
        "https://icook.tw/recipes/162577",
        "https://icook.tw/recipes/122300",
        "https://icook.tw/recipes/321648",
        "https://icook.tw/recipes/119735",
        "https://icook.tw/recipes/312336",
        "https://icook.tw/recipes/348068",
        "https://icook.tw/recipes/117692",
        "https://icook.tw/recipes/121235",
        "https://icook.tw/recipes/300920",
        "https://icook.tw/recipes/293369",
        "https://icook.tw/recipes/320936",
        "https://icook.tw/recipes/329136",
        "https://icook.tw/recipes/315014",
        "https://icook.tw/recipes/307480",
        "https://icook.tw/recipes/302616",
        "https://icook.tw/recipes/298611",
        "https://icook.tw/recipes/260493",
        "https://icook.tw/recipes/288293",
        "https://icook.tw/recipes/295269",
        "https://icook.tw/recipes/281874",
        "https://icook.tw/recipes/276299",
        "https://icook.tw/recipes/294308",
        "https://icook.tw/recipes/310629",
        "https://icook.tw/recipes/308976",
        "https://icook.tw/recipes/301920"
        ,
        "https://icook.tw/recipes/341359",
        "https://icook.tw/recipes/313727",
        "https://cookpad.com/tw/%E9%A3%9F%E8%AD%9C/2069981-%E9%87%91%E9%BB%83%E8%8A%9D%E9%BA%BB%E5%9C%B0%E7%93%9C%E9%9B%BB%E9%8D%8B%E6%96%99%E7%90%86%E5%B0%8F%E8%B3%87%E5%A5%B3%E5%81%A5%E5%BA%B7%E5%B0%8F%E9%BB%9E%E5%BF%83#",
        "https://icook.tw/recipes/257904",
        "https://icook.tw/recipes/255342",
        "https://icook.tw/recipes/252287",
        "https://icook.tw/recipes/249785",
        "https://icook.tw/recipes/247907",
        "https://icook.tw/recipes/243939",
        "https://icook.tw/recipes/243358",
        "https://peihancurious.pixnet.net/blog/post/346858232-(%E6%97%A5%E5%BC%8F%E7%83%8F%E9%BE%8D%E9%BA%B5%E4%BD%90%E6%B4%8B%E8%94%A5%E7%87%89%E9%9B%9E-%E9%A3%9F%E8%AD%9C)-%E6%87%B6%E4%BA%BA%E7%83%8F%E9%BE%8D%E9%BA%B5-%E6%B4%8B",
        "https://icook.tw/recipes/295379",
        "https://icook.tw/recipes/269008",
        "https://icook.tw/recipes/266406",
        "https://icook.tw/recipes/260215",
        "https://icook.tw/recipes/251446",
        "https://icook.tw/recipes/121409",
        "https://icook.tw/recipes/117223",
        "https://icook.tw/recipes/352403",
        "https://cookpad.com/tw/%E9%A3%9F%E8%AD%9C/6996534-%E5%B0%8F%E8%B3%87%E7%94%B7%E4%B8%AD%E6%97%A5%E6%B5%B7%E9%99%B8%E7%83%8F%E9%BE%8D%E4%B9%BE%E9%BA%B5",
        "https://icook.tw/recipes/263851",
        "https://cookpad.com/tw/%E9%A3%9F%E8%AD%9C/6835355-%E5%86%AC%E6%97%A5%E9%9B%A8%E5%AD%A3%E5%B0%8F%E8%B3%87%E7%94%B7%E6%9A%96%E5%BF%83%E6%B9%AF%E9%BA%B5",
        "https://icook.tw/recipes/262269",
        "https://icook.tw/recipes/232273",
        "https://icook.tw/recipes/265893",
        "https://icook.tw/recipes/299276",
        "https://icook.tw/recipes/297044",
        "https://icook.tw/recipes/278351",
        "https://icook.tw/recipes/267740",
        "https://icook.tw/recipes/290079",
        "https://icook.tw/recipes/259896",
        "https://icook.tw/recipes/252001",
        "https://icook.tw/recipes/352634",
        "https://icook.tw/recipes/334362",
        "https://icook.tw/recipes/322330",
        "https://icook.tw/recipes/305540",
        "https://icook.tw/recipes/297625",
        "https://icook.tw/recipes/244672",
        "https://icook.tw/recipes/241342"
        ,
        "https://icook.tw/recipes/340373",
        "https://icook.tw/recipes/323393",
        "https://icook.tw/recipes/263386",
        "https://icook.tw/recipes/122825",
        "https://icook.tw/recipes/122728",
        "https://icook.tw/recipes/343935",
        "https://icook.tw/recipes/322957",
        "https://icook.tw/recipes/319165",
        "https://icook.tw/recipes/271048",
        "https://icook.tw/recipes/243368",
        "https://www.instagram.com/p/CCLU5afjDN_/",
        "https://icook.tw/recipes/173949",
        "https://icook.tw/recipes/254174",
        "https://icook.tw/recipes/253252",
        "https://icook.tw/recipes/247316",
        "https://icook.tw/recipes/250882",
        "https://icook.tw/recipes/279306",
        "https://icook.tw/recipes/292564",
        "https://icook.tw/recipes/325250",
        "https://icook.tw/recipes/245138",
        "https://icook.tw/recipes/232265",
        "https://icook.tw/recipes/249180",
        "https://icook.tw/recipes/235067",
        "https://icook.tw/recipes/331403",
        "https://icook.tw/recipes/121444",
        "https://icook.tw/recipes/173425",
        "https://icook.tw/recipes/327034",
        "https://icook.tw/recipes/168118",
        "https://icook.tw/recipes/152087",
        "https://icook.tw/recipes/199652",
        "https://icook.tw/recipes/188409",
        "https://icook.tw/recipes/122028",
        "https://icook.tw/recipes/205366",
        "https://icook.tw/recipes/276297",
        "https://icook.tw/recipes/310211",
        "https://icook.tw/recipes/264415",
        "https://icook.tw/recipes/208026",
        "https://icook.tw/recipes/207623",
        "https://icook.tw/recipes/255243",
        "https://icook.tw/recipes/271956",
        "https://icook.tw/recipes/251678",
        "https://icook.tw/recipes/263554",
        "https://icook.tw/recipes/274579",
        "https://icook.tw/recipes/306543",
        "https://cookpad.com/tw/%E9%A3%9F%E8%AD%9C/13686421-%E8%B6%85%E7%B0%A1%E6%98%93%E7%9C%81%E9%8C%A2%E6%96%99%E7%90%86%E6%B6%BC%E6%8B%8C%E8%8F%87%E8%8F%87%E9%86%AC"
        ,
        "https://uvz90.pixnet.net/blog/post/358375766-%E3%80%90%E5%B0%8F%E8%B3%87%E5%BB%9A%E5%A8%98%E7%9C%81%E9%8C%A2%E5%81%A5%E5%BA%B7%E9%A4%90%E3%80%91%E4%B8%8A%E7%8F%AD%E6%97%8F%E5%A6%82%E4%BD%95%E6%BA%96%E5%82%99%E5%BF%AB%E9%80%9F ",
        "https://icook.tw/recipes/332917",
        "https://icook.tw/recipes/264401",
        "https://icook.tw/recipes/263550",
        "https://icook.tw/recipes/131364",
        "https://icook.tw/recipes/319684",
        "https://icook.tw/recipes/317160",
        "https://icook.tw/recipes/313189",
        "https://icook.tw/recipes/269724",
        "https://icook.tw/recipes/332794"
    )
    private val keys = arrayOf(
        "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
        "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25",
        "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40",
        "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55",
        "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70",
        "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85",
        "86", "87", "88", "89", "90", "92", "93", "94", "95", "96", "97", "98", "99", "100",
        "101", "102", "103", "104", "105", "106", "107", "108", "109", "110",
        "111", "112", "113", "114", "115", "116", "117", "118", "119", "120",
        "121", "122", "123", "124", "125", "126", "127", "128", "129", "130",
        "131", "132", "133", "134", "135", "136"
    )
    private val favs = arrayOf(
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0",
        "0"
    )

    private val allFoodList: ArrayList<FooditemsModel> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_details)//製作搜尋結果的RecyclerView

        val searchResultsItems = intent.getStringArrayListExtra("passsearched")
        val hot = intent.getStringExtra("hot")
        var searched = ""

        //initial allfoodlist
        for (i in titles.indices) {
            val model = FooditemsModel(
                images[i],
                titles[i],
                ingredients[i],
                sauces[i],
                links[i],
                keys[i],
                favs[i]
            )
            //Log.i(ContentValues.TAG,model.alphaChar)
            allFoodList.add(model)
        }
        //initial allfoodlist


        //////返回鈕//////
        val toolbar: Toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        //////返回鈕//////

        details_rv = findViewById(R.id.details_rv)
        gridLayoutManager = GridLayoutManager(
            details_rv.context, 3,
            LinearLayoutManager.VERTICAL, false
        )
        details_rv.layoutManager = gridLayoutManager
        details_rv.setHasFixedSize(true)
        if (searchResultsItems != null) {

            for (i in searchResultsItems) {
                searched += "$i "
            }
            supportActionBar?.title = "搜尋 " + searched + "的結果"

            resultList = initialList(searchResultsItems)

//        Log.i(ContentValues.TAG,resultList.toString())
//        Log.i(ContentValues.TAG,"[]")

            if (resultList.toString() == "[]") {
                Toast.makeText(applicationContext, "無 $searched 的搜尋結果", Toast.LENGTH_LONG).show()

                supportActionBar?.title = "所有料理"

                adapter = FooditemsAdapter(baseContext, this, allFoodList)

                details_rv.adapter = adapter

            }else{
                adapter = FooditemsAdapter(baseContext, this, resultList)

                details_rv.adapter = adapter

            }



        } else {
            //Toast.makeText(applicationContext,"並未輸入搜尋料理",Toast.LENGTH_SHORT).show()
            supportActionBar?.title = "為您提供" + hot + "的選項"
            val hotList:ArrayList<FooditemsModel>
            when(hot){

                "快速晚餐"->{
                    hotList = initialList(arrayListOf(
                        "鮭魚五穀米炒飯",
                        "日式豆腐丼",
                        "沙茶豬肉燴飯",
                        "培根蛋奶義大利麵",
                        "起司嫩雞塊",
                        "青檸可樂雞翅",
                        "打拋豬",
                        "咖哩洋蔥炒肉片",
                        "肉絲炒麵",
                        "番茄雞蛋麵",
                        "中日海陸烏龍乾麵",
                        "茄汁馬鈴薯",
                        "迷迭香黑胡椒馬鈴薯"
                    ))
                    adapter = FooditemsAdapter(baseContext, this, hotList)
                    details_rv.adapter = adapter
                }
                "高麗菜"->{
                    hotList = initialList(arrayListOf("高麗菜"))
                    adapter = FooditemsAdapter(baseContext, this, hotList)
                    details_rv.adapter = adapter
                }
                "馬鈴薯"->{
                    hotList = initialList(arrayListOf("馬鈴薯"))
                    adapter = FooditemsAdapter(baseContext, this, hotList)
                    details_rv.adapter = adapter
                }
                "簡易家常菜"->{
                    hotList = initialList(arrayListOf(
                        "海帶芽豆腐味噌湯",
                        "蒜頭洋蔥雞湯",
                        "麻婆豆腐",
                        "絲瓜蛋麵線",
                        "奶油香煎馬鈴薯",
                        "蜜汁雞翅",
                        "洋蔥雞丁",
                        "味噌野菜炒肉片",
                        "金沙絲瓜湯",
                        "蔥燒雞腿",
                        "咖哩生薑燒雞腿",
                        "薑汁豬肉"
                    ))
                    adapter = FooditemsAdapter(baseContext, this, hotList)
                    details_rv.adapter = adapter
                }
                "廚房三寶"->{
                    hotList = initialList(arrayListOf("蔥","薑","蒜"))
                    adapter = FooditemsAdapter(baseContext, this, hotList)
                    details_rv.adapter = adapter
                }
                "減醣"->{
                    adapter = FooditemsAdapter(baseContext, this, allFoodList)
                    details_rv.adapter = adapter
                }

            }

        }

    }


    private fun initialList(text: ArrayList<String>?): ArrayList<FooditemsModel> {

        val filteredList: ArrayList<FooditemsModel> = ArrayList()

        for (i in allFoodList) {
            if (text != null) {
                for (j in text) {
                    if ((i.alphaChar.toLowerCase(Locale.ROOT).contains(j) ||
                                i.ingredient.toLowerCase(Locale.ROOT).contains(j) ||
                                i.sauce.toLowerCase(Locale.ROOT).contains(j))
                        && i !in filteredList) {
                        //Log.i(ContentValues.TAG,"1")
                        filteredList.add(i)
                    }

                }
            }
        }

        //Log.i(ContentValues.TAG,allFoodList.toString())
        return filteredList
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onItemClick(item: FooditemsModel, position: Int) {
        val intent = Intent(baseContext, FoodDetailsf::class.java)

        intent.putExtra("FOODIMAGE", item.iconsChar.toString())
        intent.putExtra("FOODNAME", item.alphaChar)
        intent.putExtra("FOODINGREDIENT", item.ingredient)
        intent.putExtra("FOODSAUCE", item.sauce)
        intent.putExtra("FOODLINK", item.link)
        startActivity(intent)

    }

}

