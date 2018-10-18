/*
 * =====================================================================================
 *
 *       Filename:  hal_api.h
 *
 *    Description:  mcgs libhal API interface
 *
 *        Version:  1.0
 *        Created:  07/21/2017 04:35:22 PM
 *       Revision:  none
 *       Compiler:  arm-linux-gcc
 *
 *         Author:  mcgs/system.group
 *   Organization:  >>>>  mcgs <<<<
 *
 * =====================================================================================
 */

#ifndef _INCLUDE_LIBHAL_API_H_
#define _INCLUDE_LIBHAL_API_H_

#ifdef __cplusplus
    extern "C" {
#endif

/* linux header file */

/* C lib header file */
#include <stdbool.h>
#include <time.h>

/* ------------------------------------------------------------------*/
/** @defgroup api    MCGS HAL Application Framework
 *
 *  MCGS HAL functions for managing applicatons and offer api
 */
/* ------------------------------------------------------------------*/

/* ------------------------------------------------------------------*/
/** @addtogroup display     Display
 *  @ingroup api
 *
 *  Display module
 *
 *
 *  @addtogroup lcd         LCD
 *  @ingroup display
 *
 *  LCD 相关状态的获取与设置
 *
 *
 *  @{
 */
/* ------------------------------------------------------------------*/

enum
{
    HAL_DISPLAY_BACKLIGHT_OPEN,         //LCD显示背光打开状态
    HAL_DISPLAY_BACKLIGHT_CLOSE,        //LCD显示背光关闭状态
};
/* ------------------------------------------------------------------*/
/**
 * @brief 获取LCD状态
 *
 * @returns \ref HAL_DISPLAY_BACKLIGHT_OPEN : lcd 背光打开
 * @returns \ref HAL_DISPLAY_BACKLIGHT_CLOSE : lcd 背光关闭
 * @returns others: 函数执行失败
 */
/* ------------------------------------------------------------------*/
extern int hal_get_lcd_status(void);


/* ------------------------------------------------------------------*/
/**
 * @brief 设置LCD状态
 *
 * @param[in] status \ref HAL_DISPLAY_BACKLIGHT_OPEN, \ref HAL_DISPLAY_BACKLIGHT_CLOSE
 * @returns 0 ：函数执行成功
 * @returns others：函数执行失败
 */
/* ------------------------------------------------------------------*/
extern int hal_set_lcd_status(int status);

/* ------------------------------------------------------------------*/
/**
 * @brief 显示模块获取背光亮度
 *
 * @returns 非负数：当前显示模块背光亮度
 * @returns 负数：函数执行失败
 * @returns -ENODEV： 背光设备不存在
 */
/* ------------------------------------------------------------------*/
extern int hal_display_get_backlight_brightness(void);

/* ------------------------------------------------------------------*/
/**
 * @brief 显示模块设置背光亮度
 *
 * @param[in] brightness : 背光亮度百分比，最小值为1, 最大值为100,超过
 *                          该范围认为是参数不可用,
 *                          FIXME: 为什么不从0开始？
 *                              因为从用户体验来说，当背光亮度调节到最低
 *                              时，其显示屏的亮度还有一个最低亮度，此时
 *                              显示屏不会黑屏，只是亮度比较低
 *
 * @returns 0 : 函数执行成功
 * @returns others: 函数执行失败
 * @returns -ENODEV : 背光设备不存在
 */
/* ------------------------------------------------------------------*/
extern int hal_display_set_backlight_brightness(int brightness);


enum display_bootlogo_rotation
{
    DISPLAY_BOOTLOGO_RATITION_0 = 0,
    DISPLAY_BOOTLOGO_RATITION_90,
    DISPLAY_BOOTLOGO_RATITION_180,
    DISPLAY_BOOTLOGO_RATITION_270,
};
/* ------------------------------------------------------------------*/
/**
 * @brief 设置启动logo旋转功能
 *
 * @param[in] rotation : 需要设置的旋转角度
 *                      \ref DISPLAY_BOOTLOGO_RATITION_0,
 *                      \ref DISPLAY_BOOTLOGO_RATITION_90,
 *                      \ref DISPLAY_BOOTLOGO_RATITION_180,
 *                      \ref DISPLAY_BOOTLOGO_RATITION_270,
 *
 * @returns 0: 设置启动旋转成功
 * @returns others： 设置启动位图旋转失败
 */
/* ------------------------------------------------------------------*/
extern int hal_display_set_bootlogo_rotation(enum display_bootlogo_rotation rotation);

/** @} */







/* ------------------------------------------------------------------*/
/** @addtogroup sound     Sound
 *  @ingroup api
 *
 * Sound module
 *
 *
 *  @addtogroup beep    Beep
 *  @ingroup sound
 *
 * 蜂鸣控制相关的操作方法
 *
 *
 * @{
 */
/* ------------------------------------------------------------------*/

/* ------------------------------------------------------------------*/
/**
 * @brief 设置蜂鸣时间
 *
 * @param[in] time_ms : 需要设置的蜂鸣时间，单位为ms
 *
 * @returns 0       : 设置蜂鸣时间成功
 * @returns others  : 设置蜂鸣时间失败
 */
/* ------------------------------------------------------------------*/
extern int hal_set_bee_time(size_t time_ms);

/** @} */






/* ------------------------------------------------------------------*/
/** @addtogroup clock     Clock
 *  @ingroup api
 *
 * Clock module
 *
 *
 *  @addtogroup rtc     RTC
 *  @ingroup clock
 *
 *  RTC 相关的操作方法
 *
 * @note
 *      The struct tm have accurate to the second level
 *
 * @{
 */
/* ------------------------------------------------------------------*/

/* ------------------------------------------------------------------*/
/**
 * @brief 设置RTC时间
 *
 * @param[in] time : 需设置的时间, struct tm
 *
 * @returns 0       : 设置RTC时间成功
 * @returns others  : 设置RTC时间失败
 */
/* ------------------------------------------------------------------*/
extern int hal_set_rtc_time(const struct tm *time);


/* ------------------------------------------------------------------*/
/**
 * @brief 获取RTC时间
 *
 * @param[in] time : 获取的时间结果, struct tm
 *
 * @returns 0       : 获取RTC时间成功
 * @returns others  : 获取RTC时间失败
 */
/* ------------------------------------------------------------------*/
extern int hal_get_rtc_time(struct tm *time);


/* ------------------------------------------------------------------*/
/**
 * @brief 获取rtc当前状态，正常或者是异常
 *
 * @returns 0       : 当前RTC状态正常
 * @returns others  : 当前RTC状态异常
 */
/* ------------------------------------------------------------------*/
extern int hal_get_rtc_status(void);

/** @} */







/* ------------------------------------------------------------------*/
/** @addtogroup communication   Communication
 *  @ingroup api
 *
 *  Communication module
 *
 *
 *  @addtogroup uart    UART
 *  @ingroup communication
 *
 *  串口相关的方法与接口
 *
 * @{
 */
/* ------------------------------------------------------------------*/

/* ------------------------------------------------------------------*/
/**
 * @brief 串口状态参数接收结构体
 */
struct hal_uart_info
{
        int tx;		///<发送总数
        int rx;		///<接收总数
        int parity;	///<校验位错误
        int frame;	///<帧错误
        int overrun;	///<buff溢出
};
/* ------------------------------------------------------------------*/
/**
 * @brief 返回串口的具体底层信息
 *
 * @param com_num MCGS对应串口号，虚拟422无效
 * @param info 输出的数据片区
 *
 * @return 
 * 	    0 为正常返回
 * 	    负数为错误返回
 */
/* ------------------------------------------------------------------*/
extern int hal_uart_info(const int com_num, struct hal_uart_info *info);

/**
 * @brief 返回com号对应串口
 *
 * @param com_num MCGS对应串口号，虚拟422固定为9
 * @param real 输出实际的com路径，需要自己处理
 * @param size 输出buffer长度
 *
 * @return 
 * 	0 为正常
 * 	负数为错误反
 */
extern int hal_uart_cover(const int com_num, char *real, int size);

/** @} */






/* ------------------------------------------------------------------*/
/** @addtogroup net     Net
 *  @ingroup communication
 *
 *  网络相关的操作方法
 *
 * @note
 *  默认板载网卡序列为0,
 *
 * @{
 */
/* ------------------------------------------------------------------*/

/** Address types to be used by the element hal_ip_config.addr_type below
 */
enum HAL_ADDR_TYPE
{
    /** static IP address */
    HAL_ADDR_TYPE_STATIC = 0,
    /** Dynamic  IP address*/
    HAL_ADDR_TYPE_DHCP = 1,
    /** Link level address */
    HAL_ADDR_TYPE_LLA = 2,
};

enum HAL_NETWORK_STATUS
{
    /** network status , up*/
    HAL_NETWORK_STATUS_LINK_UP = 0,
    /** network status , down*/
    HAL_NETWORK_STATUS_LINK_DOWN = 1,
};


/** This data structure represents an IPv4 address */
struct hal_ipv4_config
{
    /** Set to \ref ADDR_TYPE_DHCP to use DHCP to obtain the IP address or
     *  \ref ADDR_TYPE_STATIC to use a static IP. In case of static IP
     *  Address ip, gw, netmask and dns members must be specified.  When
     *  using DHCP, the ip, gw, netmask and dns are overwritten by the
     *  values obtained from the DHCP Server. They should be zeroed out if
     *  not used. */
    unsigned addr_type:2;
    /** The system's IP address in network order. */
    unsigned address;
    /** The system's default gateway in network order. */
    unsigned gw;
    /** The system's subnet mask in network order. */
    unsigned netmask;
    /** The system's primary dns server in network order. */
    unsigned dns1;
    /** The system's secondary dns server in network order. */
    unsigned dns2;
};

#ifdef CONFIG_IPV6

/** This data structure represents an IPv6 address */
    struct hal_ipv6_config
{
    /** The system's IPv6 address in network order. */
    unsigned address[4];
    /** The address type: linklocal, site-local or global. */
    unsigned char addr_type;
    /** The state of IPv6 address (Tentative, Preferred, etc). */
    unsigned char addr_state;
};
#endif

/** Network IP configuration.
 *
 *  This data structure represents the network IP configuration
 *  for IPv4 as well as IPv6 addresses
 */
struct hal_ip_config
{
#ifdef CONFIG_IPV6
    /** The network IPv6 address configuration that should be
     * associated with this interface. */
    struct hal_ipv6_config ipv6;
#endif
    /** The network IPv4 address configuration that should be
     * associated with this interface. */
    struct hal_ipv4_config ipv4;

    /** if set ipv4 used the dhcp, The function max time return
     * @note:
     * dhcp_timeout = 0, wait dhcp setting forever
     * dhcp_timeout = others(ms), wait hdcp others ms
     * */
    unsigned dhcp_timeout;
};

/** The space reserved for storing network names, \ref hal_network */
#define HAL_NETWORK_NAME_MAX_LENGTH	32

struct hal_network
{
    /** The name of this network profile.  Each network profile that is
     *  added to the WLAN Connection Manager must have a unique name. */
    char name[HAL_NETWORK_NAME_MAX_LENGTH];

    /** The network IP address configuration specified by struct
     * wlan_ip_config that should be associated with this interface. */
    struct hal_ip_config ip;


    /* Private Fields */
    /** The network current runing status
     * \ref HAL_NETWORK_STATUS_LINK_UP
     * \ref HAL_NETWORK_STATUS_LINK_DOWN
     * */
    unsigned network_status;
};

/* ------------------------------------------------------------------*/
/**
 * @brief 启动一个网卡，包括给网卡模块供并初始化
 *
 * @param[in] name : 网卡接口名字，该名字应从\ref hal_get_network 函数获取
 *
 * @returns 0: 启动该网卡成功
 * @returns others; 启动该网卡失败
 */
/* ------------------------------------------------------------------*/
extern int hal_start_network(const char *name);

/* ------------------------------------------------------------------*/
/**
 * @brief 关闭一个网卡
 *
 * @param[in] name : 网卡接口名字，该名字应从\ref hal_get_network 函数获取
 *
 * @returns 0: 关闭该网卡成功
 * @returns others; 关闭该网卡失败
 */
/* ------------------------------------------------------------------*/
extern int hal_stop_network(const char *name);

/* ------------------------------------------------------------------*/
/**
 * @brief 设置一个网卡IP地址
 *
 * @param[in] name : 网卡接口名字，该名字应从\ref hal_get_network 函数获取
 * @param[in] addr : IP 地址结构体, define:\ref hal_network
 *
 * @returns 0: 设置IP地址成功
 * @returns others: 设置IP地址失败
 */
/* ------------------------------------------------------------------*/
extern int hal_set_ip_addr(const char *name, struct hal_ip_config *addr);

/* ------------------------------------------------------------------*/
/**
 * @brief 获取一个网卡信息，默认的，当index参数为0时，表示当前板载网卡
 *
 * @param[in] index : 网卡序号，板载网卡的序号为0
 * @param[out] network ： 网卡信息，\ref hal_network
 *
 * @returns 0: 获取网卡信息成功
 * @returns others: 获取网卡信息失败
 */
/* ------------------------------------------------------------------*/
extern int hal_get_network(unsigned int index, struct hal_network *network);

/* ------------------------------------------------------------------*/
/**
 * @brief 获取一个网卡信息，通过网卡名字获取
 *
 * @param[in] name : 网卡名字， 其占用空间最大为 \ref HAL_NETWORK_NAME_MAX_LENGTH
 * @param[out] network ： 网卡信息，\ref hal_network
 *
 * @returns 0: 获取网卡信息成功
 * @returns others: 获取网卡信息失败
 */
/* ------------------------------------------------------------------*/
extern int hal_get_network_byname(char *name, struct hal_network *network);

/* ------------------------------------------------------------------*/
/**
 * @brief 获取当前系统网卡数量
 *
 * @param[out] count : 保存当前系统网卡数量值
 *
 * @returns 0: 获取当前系统网卡数量成功
 * @returns others: 获取当前系统网卡数量失败
 */
/* ------------------------------------------------------------------*/
extern int hal_get_network_count(unsigned int *count);

/* ------------------------------------------------------------------*/
/**
 * @brief 获取网卡MAC地址
 *  This function copies the MAC address of the wireless interface to
 *  the 6-byte array pointed to by \a dest.  In the event of an error, nothing
 *  is copied to \a dest.
 *
 * @param[in] name : 网卡名字， 其占用空间最大为 /ref HAL_NETWORK_NAME_MAX_LENGTH
 * @param[out] dest : 保存网卡MAC地址，该指针长度，不小于6个字节
 *
 * @returns 0: 获取网卡MAC地址成功
 * @returns others: 获取网卡MAC地址失败
 */
/* ------------------------------------------------------------------*/
extern int hal_get_mac_address(const char *name, unsigned char *dest);

/** @} */



/* ------------------------------------------------------------------*/
/** @addtogroup usb     USB
 *  @ingroup api
 *
 * USB模块相关操作方法
 *
 * @{
 */
/* ------------------------------------------------------------------*/

enum
{
    HAL_USB_OTG_MASTER,         /// USB OTG 主机模式
    HAL_USB_OTG_DEVICE,         /// USB OTG 从机模式
    HAL_USB_OTG_UNKNOWN,        /// USB OTG 无效模式
};
/* ------------------------------------------------------------------*/
/**
 * @brief 获取usb当前模式
 *
 * @return  \ref HAL_USB_OTG_MASTER     : host  主口模式
 * @return  \ref HAL_USB_OTG_DEVICE     : device 从口模式
 * @return  \ref HAL_USB_OTG_UNKNOWN    : null 无效模式
 * @return  others                      : 获取USB模式失败
 */
/* ------------------------------------------------------------------*/
extern int hal_get_usb_mode(void);

/* ------------------------------------------------------------------*/
/**
 * @brief 设置usb当前模式
 *
 * @param[in] mode  :   模式: \ref HAL_USB_OTG_MASTER , \ref HAL_USB_OTG_DEVICE,
 *                            \ref HAL_USB_OTG_UNKNOWN
 * @returns 0       :   模式设置成功
 * @returns others  :   函数执行失败
 */
/* ------------------------------------------------------------------*/
extern int hal_set_usb_mode(int mode);

/** @} */



/* ------------------------------------------------------------------*/
/** @addtogroup netlink     Netlink
 *  @ingroup api
 *
 *  Netlink 相关通信接口定义
 *
 * @note
 *  目前暂只支持唯一ID和平台信息获取
 *
 * @{
 */
/* ------------------------------------------------------------------*/

/** attributes */
enum
{
    HAL_NETLINK_CMD_ATTR_UNSPEC = 0,

    /** req struct  */
    HAL_NETLINK_CMD_ATTR_STRUCT_REQ,
    /** answer struct */
    HAL_NETLINK_CMD_ATTR_STRUCT_RESP,

    __HAL_NETLINK_CMD_ATTR_MAX,
    HAL_NETLINK_CMD_ATTR_MAX = __HAL_NETLINK_CMD_ATTR_MAX - 1,
};


/* command */
enum
{
    HAL_NETLINK_CMD_UNSPEC = 0,

    /** get unique ID */
    HAL_NETLINK_CMD_GET_ID,

    /** get system platform info */
    HAL_NETLINK_CMD_GET_PLATFORM_INFO,

    /** get system platform authorize */
    HAL_NETLINK_CMD_GET_PLATFORM_AUTH,

    __HAL_NETLINK_CMD_MAX,
    HAL_NETLINK_CMD_MAX = __HAL_NETLINK_CMD_MAX - 1,
};



/* for get id */
#define HAL_NETLINK_SALT_MAX_LENGTH	    (32)
#define HAL_NETLINK_ID_MAX_LENGTH	    (32)
struct hal_netlink_cmd_get_id_req
{
    /** used to encrypte id, maybe is a uuid */
    unsigned char salt[HAL_NETLINK_SALT_MAX_LENGTH];
    /** The slat real length */
    unsigned salt_len;
};

struct hal_netlink_cmd_get_id_resp
{
    /** request status value, when is "0" is success */
    int status;

    /** have receive request salt */
    unsigned char salt[HAL_NETLINK_SALT_MAX_LENGTH];
    /** id: haved encrypt by salt */
    unsigned char id[HAL_NETLINK_ID_MAX_LENGTH];
    /** output id length */
    unsigned id_len;
};


/* for get platform info */
#define HAL_NETLINK_NAME_MAX_LEN    (32)
struct hal_netlink_cmd_get_platform_info_resp
{
    /** request status value, when is "0" is success */
    int status;

    /** NK release version information  */
    char nk_release_version[HAL_NETLINK_NAME_MAX_LEN];
    /** NK release type */
    char nk_release_type[HAL_NETLINK_NAME_MAX_LEN];
    /** NK release date */
    char nk_release_date[HAL_NETLINK_NAME_MAX_LEN];
    /** NK build git version */
    char nk_build_version[HAL_NETLINK_NAME_MAX_LEN];

    /** hal information */
    char hal_release_version[HAL_NETLINK_NAME_MAX_LEN];
};


/* for get platform authorize */
#define HAL_NETLINK_AUTHORIZE_CONTENT_LEN   (1020)
struct hal_netlink_cmd_get_platform_auth_resp
{
    /** request status value, when is "0" is success */
    int status;

    /* register code  fied*/
    unsigned short header;
    unsigned short len;
    unsigned char code_contex[HAL_NETLINK_AUTHORIZE_CONTENT_LEN];
};


/* netlink-generic related info */
/** netlink id name */
#define HAL_NETLINK_GENL_NAME "mcgs-hal"
/** netlink id version */
#define HAL_NETLINK_GENL_VERSION 0x1

/** @} */

#ifdef __cplusplus
    }
#endif


#endif //end of the file
