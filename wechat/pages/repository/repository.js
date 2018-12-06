// pages/repository/repository.js
const config = require('../../utils/config.js');
const appInstance = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    location: appInstance.globalData.defaultCity,
    county: appInstance.globalData.defaultCounty,
    lawList: [{
      "name": "张三",
      "hardImg": "https://i.loli.net/2017/08/21/599a521472424.jpg",
      "company": "深圳市哦哦律师事务所",
      "adreess": "深圳市南山区南山大道与创业路交汇处亿利达大厦1栋518",
      "service": "债务债权,经济纠纷,婚姻家庭",
    },
    {
      "name": "李四",
      "hardImg": "https://i.loli.net/2017/08/21/599a521472424.jpg",
      "company": "深圳市哦哦律师事务所",
      "adreess": "深圳市南山区南山大道与创业路交汇处亿利达大厦1栋518",
      "service": "债务债权,经济纠纷,婚姻家庭",
    },
    {
      "name": "王五",
      "hardImg": "https://i.loli.net/2017/08/21/599a521472424.jpg",
      "company": "深圳市哦哦律师事务所",
      "adreess": "深圳市南山区南山大道与创业路交汇处亿利达大厦1栋518",
      "service": "债务债权,经济纠纷,婚姻家庭",
    },
    {
      "name": "赵六",
      "hardImg": "https://i.loli.net/2017/08/21/599a521472424.jpg",
      "company": "深圳市哦哦律师事务所",
      "adreess": "深圳市南山区南山大道与创业路交汇处亿利达大厦1栋518",
      "service": "债务债权,经济纠纷,婚姻家庭",
    },
    {
      "name": "张三",
      "hardImg": "https://i.loli.net/2017/08/21/599a521472424.jpg",
      "company": "深圳市哦哦律师事务所",
      "adreess": "深圳市南山区南山大道与创业路交汇处亿利达大厦1栋518",
      "service": "债务债权,经济纠纷,婚姻家庭",
      }, ,
      {
        "name": "王五",
        "hardImg": "https://i.loli.net/2017/08/21/599a521472424.jpg",
        "company": "深圳市哦哦律师事务所",
        "adreess": "深圳市南山区南山大道与创业路交汇处亿利达大厦1栋518",
        "service": "债务债权,经济纠纷,婚姻家庭",
      },
      {
        "name": "赵六",
        "hardImg": "https://i.loli.net/2017/08/21/599a521472424.jpg",
        "company": "深圳市哦哦律师事务所",
        "adreess": "深圳市南山区南山大道与创业路交汇处亿利达大厦1栋518",
        "service": "债务债权,经济纠纷,婚姻家庭",
      },
      {
        "name": "张三",
        "hardImg": "https://i.loli.net/2017/08/21/599a521472424.jpg",
        "company": "深圳市哦哦律师事务所",
        "adreess": "深圳市南山区南山大道与创业路交汇处亿利达大厦1栋518",
        "service": "债务债权,经济纠纷,婚姻家庭",
      },
    ]
  },
  getDetails:function(data){
console.log(data)
    console.log(data.currentTarget.dataset.id)
    wx.switchTab({
      url: '/pages/card/card'
    })
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.getLocation();
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    this.setData({
      location: appInstance.globalData.defaultCity,
      county: appInstance.globalData.defaultCounty
    })
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  },
  getLocation: function () {
    console.log("正在定位城市");
    this.setData({
      county: '',
      location:''
    })
    const that = this;
    wx.getLocation({
      type: 'wgs84',
      success: function (res) {
        let latitude = res.latitude
        let longitude = res.longitude
        wx.request({
          url: `https://apis.map.qq.com/ws/geocoder/v1/?location=${latitude},${longitude}&key=${config.key}`,
          success: res => {
            // console.log(res)
            // console.log(res.data.result.ad_info.city+res.data.result.ad_info.adcode);
            that.setData({
              location: res.data.result.ad_info.city,
              county: res.data.result.ad_info.district
            })
            // that.selectCounty();
          }
        })
      }
    })
  },
})