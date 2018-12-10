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
    law:"",
    lawList: [],
    spinShow: true,
    isSearch:false,
    loading:false,
    more:false,
    moreBottom:false,
    void1:false,
    pageNum:1,
    isDetails:true
  },
  getDetails:function(data){
    this.setData({ isDetails: false })
    const id = data.currentTarget.dataset.id
    wx.navigateTo({
      url: `/pages/cardDetails/cardDetails?id=${id}`
    })
    
  },
  setDetails:function(){
    this.setData({ isDetails: true })
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
  onShow: function (e) {
    if (this.data.isDetails){
      if (appInstance.globalData.selectCity){
        this.setData({
          location: appInstance.globalData.defaultCity,
          county: appInstance.globalData.defaultCounty,
        })

      }
    this.setData({
      pageNum: 1,
      lawList: [],
    })
    this.search()
    }
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
   
    if (!this.data.moreBottom){
      this.setData({
        pageNum: ++this.data.pageNum,
        loading:true,
        isDetails: true
      })
      this.search()
    }
   
    console.log("最底了=====")
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
              county: res.data.result.ad_info.district,
              isSearch:true
            })
            that.search();
            // that.selectCounty();
          },
          fail: res => {
            console.log("获取位置失败")
          }
        })
      },
      fail: res => {
        console.log("获取位置失败77")         
        that.setData({
          isSearch: true,
          location: appInstance.globalData.defaultCity,
          county: appInstance.globalData.defaultCounty,
        })
        that.search();
      }
     
    })
    
  },
  search1:function(e){
    this.setData({
      pageNum:1,
      lawList:[],
      law: e.detail.value,
      isDetails: true
    })
    this.search(e)
  },
  search:function(e){
    if (!this.data.isSearch){
      return false;
    }
    const data = { address: this.data.county ? this.data.county : this.data.location }
    data.firstName = e!=undefined?e.detail.value:this.data.law;
    data.pageNum = this.data.pageNum
    var that = this;
    wx.request({
      url: appInstance.globalData.baseService + "user/search",
      header: {
        "Content-Type": "application/x-www-form-urlencoded", 'Cookie': wx.getStorageSync("cookie")
      },
      method: "POST",
      data: data,
      //data: { code: code},
      complete: function (res) {
        if(res.data.code==200){
          let list = []
          if (res.data.data.records.length == 0) {
            list = res.data.data.records
          } else {
            list = that.data.lawList.concat(res.data.data.records)
          }
          const lgth = res.data.data.records.length;
          that.setData({
            lawList: list,
            spinShow: false,
            moreBottom: (lgth < 10 ? true : false),
              more: ((lgth < 10 && lgth>0)? true : false),
            void1: (lgth == 0 ? true : false),
            loading: false
          })
        }else{
          appInstance.loginCode()
        }


      }
    })
  }
})