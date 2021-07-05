/*(1)視覺展示系統:負責遊戲畫面展示的互動*/
var displaySystem = {

    //1.擊中訊息展示:依據玩家是否擊中更改提示訊息
    displayMessage: function (msg) {
        var messageHint = document.getElementById("hintMessage");
        messageHint.innerHTML = msg;
    },

    //2.擊中後表格上圖片展示
    displayHit: function (location) {
        var hitSite = document.getElementById(location);

        setTimeout(function () { hitSite.setAttribute("class", "explosion"); }, 0);
        setTimeout(function () { hitSite.setAttribute("class", "hit"); }, 1500);
        setTimeout(function () { hitSite.setAttribute("class", "skull"); }, 4000);

        // hitSite.setAttribute("class", "hit");


        //擊中後加分
        // var scoreText = document.getElementById("setScore");
        // var newScore = parseInt(scoreText.innerHTML) + 50;
        // scoreText.innerHTML = newScore;

        //擊中後獲得金錢

        initSystem.myMoney += 30;
        this.displayMoney();

    },

    //3.未擊中表格圖片展示
    displayMiss: function (location) {
        var missSite = document.getElementById(location);

        setTimeout(function () { missSite.setAttribute("class", "explosion"); }, 0);
        setTimeout(function () { missSite.setAttribute("class", "miss"); }, 1500);



    },

    //4.雷達掃描展示
    displayRaydaGet: function (location) {
        var scann = document.getElementById(location);

        setTimeout(function () { scann.setAttribute("class", "radar"); }, 0);
        setTimeout(function () { scann.setAttribute("class", "terr"); }, 3500);
        setTimeout(function () { scann.setAttribute("class", ""); }, 4000);
        setTimeout(function () { scann.setAttribute("class", "terr"); }, 4200);
        setTimeout(function () { scann.setAttribute("class", ""); }, 4500);


        displaySystem.displayMessage("掃描中");

    },

    displayRaydaMiss: function (location) {
        var scann = document.getElementById(location);

        setTimeout(function () { scann.setAttribute("class", "radar"); }, 0);
        setTimeout(function () { scann.setAttribute("class", ""); }, 3500);


        displaySystem.displayMessage("掃描中");

    },



    //5.商店與軍火庫炸彈數量顯示
    displayBombNumber: function () {
        var renewLit = document.getElementById("littleBombNum");
        var renewDis = document.getElementById("disperBombNum");
        var renewRay = document.getElementById("Rayda");

        renewLit.innerHTML = initSystem.littleBombNum;
        renewDis.innerHTML = initSystem.dispersedBombNum;
        renewRay.innerHTML = initSystem.rayDaNum;

    },

    //6.展示金錢
    displayMoney: function () {

        var addmoney = document.getElementById("setMoney");
        var newMoney = initSystem.myMoney;
        addmoney.innerHTML = newMoney;

    },
    //7.展示恐怖分子人數
    displayTerrNum: function () {
        var getTerrNum = document.getElementById("terrNum");
        var newTerrNum = (initSystem.terrlocations.length) - (fireSystem.hitedCount);
        getTerrNum.innerHTML = newTerrNum;
    },
    //8.展示時間
    displayTime: function () {
        var getTime = document.getElementById("setTime");
        var newtime = (initSystem.playerTime) / 1000;
        getTime.innerHTML = newtime;

    }

};
//-------------------------------------------------------------------------------
/*(2)遊戲射擊系統 */
var fireSystem = {
    //1.各項基礎屬性
    bombingLocations: [],
    finishBomb: [],
    hitedCount: 0,
    dispersedBombSite: [],

    littleBombFire: false,
    dispersedBombFire: false,
    raydaFire: false,

    //2.小型炸彈開關
    switchOfLittleBomb: function () {
        fireSystem.playAudio("changeWeapen");
        console.log("載入小型炸彈模式");

        var getLitButton = document.getElementById("littleBombButton");
        getLitButton.setAttribute("class", "flash");

        var getDisButton = document.getElementById("disButton");
        getDisButton.setAttribute("class", "selectBomb");

        var getRayButton = document.getElementById("RayButton");
        getRayButton.setAttribute("class", "selectBomb");


        fireSystem.littleBombFire = true;
        fireSystem.dispersedBombFire = false;
        fireSystem.raydaFire = false;

    },

    //3.隨機轟炸開關
    switchOfdispersedBomb: function () {
        console.log("使用隨機轟炸");

        var getDisButton = document.getElementById("disButton");
        getDisButton.setAttribute("class", "flash");

        var getLitButton = document.getElementById("littleBombButton");
        getLitButton.setAttribute("class", "selectBomb");


        var getRayButton = document.getElementById("RayButton");
        getRayButton.setAttribute("class", "selectBomb");

        if (initSystem.dispersedBombNum == 0) {
            fireSystem.playAudio("outOf");
            displaySystem.displayMessage("彈藥不足");
        }

        fireSystem.littleBombFire = false;
        fireSystem.dispersedBombFire = true;
        fireSystem.raydaFire = false;
    },

    //4.雷達開關
    switchOfRayda: function () {
        console.log("使用雷達掃描");

        var getRayButton = document.getElementById("RayButton");
        getRayButton.setAttribute("class", "flash");

        var getLitButton = document.getElementById("littleBombButton");
        getLitButton.setAttribute("class", "selectBomb");

        var getDisButton = document.getElementById("disButton");
        getDisButton.setAttribute("class", "selectBomb");


        fireSystem.littleBombFire = false;
        fireSystem.dispersedBombFire = false;
        fireSystem.raydaFire = true;
    },

    //5.小型炸彈攻擊:回傳玩家點擊地點
    playerClickLittle: function (id) {

        if (fireSystem.littleBombFire == true) {

            if (initSystem.littleBombNum > 0) {

                displaySystem.displayMessage("請選擇轟炸位置");

                if ((fireSystem.bombingLocations.indexOf(id)) == -1 && this.finishBomb.indexOf(id) == -1) {
                    //設定預定轟炸位置圖片，加入音效與特效


                    fireSystem.playAudio("putBomb");
                    var prehitSite = document.getElementById(id);

                    prehitSite.setAttribute("class", "prehit");

                    //將預定轟炸位置放入指定陣列中
                    fireSystem.bombingLocations.push(id);
                    initSystem.littleBombNum -= 1;

                    //展示剩餘炸彈數量
                    displaySystem.displayBombNumber();
                    console.log(fireSystem.bombingLocations);
                } else if (this.finishBomb.indexOf(id) == -1) {

                    //重複點選將取消原定轟炸位置(將圖片改為空)

                    var prehitSite = document.getElementById(id);
                    prehitSite.setAttribute("class", "");
                    initSystem.littleBombNum += 1;

                    //展示剩餘炸彈數量
                    displaySystem.displayBombNumber();
                    console.log(fireSystem.bombingLocations);

                    //移除原先指定的炸彈位置
                    var findPosition = fireSystem.bombingLocations.indexOf(id);
                    console.log(`想要刪除的位置:${findPosition}`);
                    fireSystem.bombingLocations.splice(findPosition, 1);

                }

            } else if (initSystem.littleBombNum == 0 && fireSystem.bombingLocations.indexOf(id) != -1) {
                displaySystem.displayMessage("請選擇轟炸位置");
                var prehitSite = document.getElementById(id);
                prehitSite.setAttribute("class", "");
                initSystem.littleBombNum += 1;

                //展示剩餘炸彈數量
                displaySystem.displayBombNumber();
                console.log(fireSystem.bombingLocations);

                //移除原先指定的炸彈位置
                var findPosition = fireSystem.bombingLocations.indexOf(id);
                console.log(`想要刪除的位置:${findPosition}`);
                fireSystem.bombingLocations.splice(findPosition, 1);

                console.log(fireSystem.bombingLocations);


            } else {
                fireSystem.playAudio("outOf");
                displaySystem.displayMessage("彈藥不足！");

                console.log(fireSystem.bombingLocations);

            }

        }

    },

    //6.隨機轟炸啟動
    dispersedBomb: function () {
        if (fireSystem.dispersedBombFire == true && initSystem.dispersedBombNum > 0) {
            if (this.finishBomb.length < 77 && this.dispersedBombSite.length < 77) {
                displaySystem.displayMessage("隨機轟炸");
                var dispersedBomb = 5;

                //產生隨機位置
                for (var i = 1; i <= dispersedBomb; i++) {
                    var BombSite = (Math.floor(Math.random() * 89) + 10).toString();

                    if (initSystem.removeLocation.indexOf(BombSite) == -1 && this.dispersedBombSite.indexOf(BombSite) == -1 && this.finishBomb.indexOf(BombSite) == -1) {
                        this.dispersedBombSite.push(BombSite);

                        console.log(this.dispersedBombSite.sort());
                        fireSystem.bombingLocations.push(BombSite);



                    } else {
                        i -= 1;
                    }

                }
                initSystem.dispersedBombNum -= 1;
                displaySystem.displayBombNumber();
                console.log(fireSystem.bombingLocations.sort());
                // console.log(`顯示預定轟炸的位置:${fireSystem.bombingLocations.sort()}`)
            } else if (this.finishBomb.length >= 77 && this.finishBomb.length <= 81) {
                console.log(`隨機轟炸位置小於5，以轟炸數量${this.finishBomb.length}`);
                displaySystem.displayMessage("隨機轟炸");
                var dispersedBombed = 81 - (this.finishBomb.length);

                for (var i = 1; i <= dispersedBombed; i++) {
                    var BombSite = (Math.floor(Math.random() * 89) + 10).toString();

                    if (initSystem.removeLocation.indexOf(BombSite) == -1 && this.dispersedBombSite.indexOf(BombSite) == -1 && this.finishBomb.indexOf(BombSite) == -1) {
                        this.dispersedBombSite.push(BombSite);

                        console.log(this.dispersedBombSite.sort());
                        fireSystem.bombingLocations.push(BombSite);


                    } else {
                        i -= 1;
                    }
                }

            } else {
                fireSystem.playAudio("outOf");
                displaySystem.displayMessage("已全數轟炸完畢");
                console.log(`已全數轟炸完畢`);
            }
        }
        this.fire(fireSystem.bombingLocations);


    },

    //7.雷達掃描系統
    rayDa: function () {
        if (initSystem.rayDaNum == 0) {
            console.log("雷達衝能不足!");
            fireSystem.playAudio("outOf");
            displaySystem.displayMessage("雷達衝能不足！");
        }

        rayDaSite = [];

        if (initSystem.rayDaNum > 0) {
            if (this.finishBomb.length < 72) {
                displaySystem.displayMessage("雷達掃描中");
                var rayDaShow = 10;
                fireSystem.playAudio("rayDa");
                //產生隨機位置
                for (var i = 1; i <= rayDaShow; i++) {
                    var rayDaShowSite = (Math.floor(Math.random() * 89) + 10).toString();

                    if (initSystem.removeLocation.indexOf(rayDaShowSite) == -1 && this.finishBomb.indexOf(rayDaShowSite) == -1 && rayDaSite.indexOf(rayDaShowSite) == -1) {

                        rayDaSite.push(rayDaShowSite);
                        console.log(rayDaSite.sort());
                    } else {
                        i -= 1;
                    }

                }
                for (var k = 0; k < rayDaSite.length; k++) {
                    // console.log(locations[k]);
                    var checkPosition = initSystem.terrlocations.indexOf(rayDaSite[k]);
                    // console.log(`檢查值:${checkPosition}`);
                    if (checkPosition == -1) {

                        displaySystem.displayRaydaMiss(rayDaSite[k]);

                        console.log(`掃描位置:${rayDaSite[k]}沒東西`);
                    } else {

                        displaySystem.displayRaydaGet(rayDaSite[k]);

                        console.log(`掃描位置:${rayDaSite[k]}抓到!`);
                    }


                }

                //清空預定轟炸的陣列
                rayDaSite.splice(0, rayDaSite.length);
                console.log(`清除後的預訂掃描位置:${rayDaSite}`);

                initSystem.rayDaNum -= 1;
                displaySystem.displayBombNumber();



            }

            else {

                displaySystem.displayMessage("彈藥不足！");
                console.log(`沒有雷達了`);
            }
        }

    },



    //8.發射功能:檢查是否擊中
    fire: function (locations) {
        for (var k = 0; k < locations.length; k++) {
            // console.log(locations[k]);
            var checkPosition = initSystem.terrlocations.indexOf(locations[k]);
            // console.log(`檢查值:${checkPosition}`);
            if (checkPosition == -1) {
                displaySystem.displayMiss(locations[k]);
                fireSystem.playAudio("explosion");

                this.finishBomb.push(locations[k]);
                console.log(`轟炸位置:${locations[k]}未擊中`);
            } else {
                fireSystem.playAudio("explosion");
                displaySystem.displayHit(locations[k]);
                this.finishBomb.push(locations[k]);
                fireSystem.hitedCount += 1;
                console.log(`轟炸位置:${locations[k]}擊中`);
            }

            console.log(`已轟炸過的位置:${this.finishBomb.sort()}`);
        }

        //清空預定轟炸的陣列
        fireSystem.bombingLocations.splice(0, fireSystem.bombingLocations.length);
        displaySystem.displayTerrNum();
        console.log(`顯示轟炸後清除的陣列:${fireSystem.bombingLocations}`);
    },

    //9.撥放音效
    playAudio: function (id) {
        var getAudio = document.getElementById(id);
        getAudio.volume = 0.5;
        getAudio.play();



    }


};
//-------------------------------------------------------------------------------
/*(3)初始化系統*/
var initSystem = {

    myMoney: 0,
    littleBombNum: 0,
    dispersedBombNum: 0,
    rayDaNum: 0,
    playerTime: 0,

    terrlocations: [],
    removeLocation: ["19", "29", "39", "49", "59", "69", "79", "89"],


    initStart: function () {
        fireSystem.playAudio("gameStart");

        this.littleBombNum = 5;
        this.playerTime = 70000;
        initSystem.myMoney = 150;
        initSystem.dispersedBombNum = 1;
        initSystem.rayDaNum = 1;

        initSystem.generateTerr();

        displaySystem.displayMessage("任務開始！");
        displaySystem.displayBombNumber();
        displaySystem.displayMoney();
        displaySystem.displayTerrNum();

        initSystem.countTime();

        var failPic = document.getElementById("failPic");
        failPic.style.display = "none";

        var failPic = document.getElementById("startButton");
        failPic.style.display = "none";


    },

    restartGame: function () {
        location.reload();
    },



    //隨機產生恐怖分子人數與位置
    generateTerr: function () {
        //產生隨機人數，範圍20~50
        var terrNumbers = Math.floor(Math.random() * (31)) + 20;


        //產生隨機位置
        for (var i = 1; i <= terrNumbers; i++) {
            var newTerr = (Math.floor(Math.random() * (89)) + 10).toString();
            if (initSystem.terrlocations.indexOf(newTerr) == -1 && this.removeLocation.indexOf(newTerr) == -1) {
                initSystem.terrlocations.push(newTerr);
            } else {
                i -= 1;
            }
        }
        console.log(`已生成${terrNumbers}個恐怖分子，座標:${initSystem.terrlocations.sort()}`);
        return initSystem.terrlocations.sort()
    },



    countTime: function () {
        if (initSystem.playerTime > 0 && fireSystem.hitedCount != initSystem.terrlocations.length) {
            console.log((initSystem.playerTime / 1000) + " sec...");
            setTimeout(initSystem.countTime, 1000);
            initSystem.playerTime -= 1000;

            displaySystem.displayTime();



        } else if (fireSystem.hitedCount == initSystem.terrlocations.length) {
            fireSystem.playAudio("victory");

            displaySystem.displayMessage("任務成功");
            console.log("成功!");

            var failPic = document.getElementById("winPic");
            failPic.style.display = "inline";
        } else {
            //倒數完成
            fireSystem.playAudio("failMusic");
            displaySystem.displayMessage("任務失敗");
            // fireSystem.playAudio("failMusic");


            console.log("時間到,失敗");

            initSystem.littleBombNum = 0;
            initSystem.myMoney = 0;
            initSystem.dispersedBombNum = 0;
            initSystem.rayDaNum = 0;
            displaySystem.displayBombNumber();
            displaySystem.displayMoney();
            displaySystem.displayTerrNum();

            var failPic = document.getElementById("failPic");
            failPic.style.display = "inline";

        }







    }

};
//-------------------------------------------------------------------------------
/*(4)武器商店*/
var weaponShop = {

    buyLittleBomb: function () {
        console.log(`目前金錢數字:${initSystem.myMoney}`);
        if (initSystem.myMoney >= 20) {
            fireSystem.playAudio("getMoney");
            initSystem.littleBombNum += 1;
            initSystem.myMoney -= 20;


            displaySystem.displayBombNumber();
            displaySystem.displayMoney();

        } else {
            console.log("餘額不足");
        }



    },


    buyDispersedBomb: function () {

        console.log(`目前金錢數字:${initSystem.myMoney}`);
        if (initSystem.myMoney >= 100) {
            fireSystem.playAudio("getMoney");
            initSystem.dispersedBombNum += 1;
            initSystem.myMoney -= 100;


            displaySystem.displayBombNumber();
            displaySystem.displayMoney();

        } else {
            console.log("餘額不足");
        }




    },

    buyRayda: function () {

        console.log(`目前金錢數字:${initSystem.myMoney}`);
        if (initSystem.myMoney >= 120) {
            fireSystem.playAudio("getMoney");
            initSystem.rayDaNum += 1;
            initSystem.myMoney -= 120;


            displaySystem.displayBombNumber();
            displaySystem.displayMoney();

        } else {
            console.log("餘額不足");
        }



    },





}





//-------------------------------------------------------------------------------










