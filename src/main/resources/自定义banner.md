# 1 主方法
```java
Banner printedBanner = printBanner(environment);
```
# 2 SpringApplicationBannerPrinter (获取banner并打印)
```java
Banner print(Environment environment, Class<?> sourceClass, PrintStream out) {
    Banner banner = getBanner(environment);
    banner.printBanner(environment, sourceClass, out);
    return new PrintedBanner(banner, sourceClass);
}
```
## 2.1 getBanner(优先判断是否存在图片或文本,若是图片或文本都不存在，返回默认banner)
```java
private Banner getBanner(Environment environment) {
    Banners banners = new Banners();
    banners.addIfNotNull(getImageBanner(environment));
    banners.addIfNotNull(getTextBanner(environment));
    if (banners.hasAtLeastOneBanner()) {
        return banners;
    }
    if (this.fallbackBanner != null) {
        return this.fallbackBanner;
    }
    return DEFAULT_BANNER;
}

private static final Banner DEFAULT_BANNER = new SpringBootBanner();
```
```java
// 图片格式
static final String[] IMAGE_EXTENSION = { "gif", "jpg", "png" };

// 加载以banner为文件名的资源
Resource resource = this.resourceLoader.getResource("banner." + ext);

// 文本路径
static final String DEFAULT_BANNER_LOCATION = "banner.txt";
```

# 3 打印banner
```java
public void printBanner(Environment environment, Class<?> sourceClass, PrintStream out) {
    try {
        // 1 转换字符串
        String banner = StreamUtils.copyToString(this.resource.getInputStream(),
                environment.getProperty("spring.banner.charset", Charset.class, StandardCharsets.UTF_8));

        for (PropertyResolver resolver : getPropertyResolvers(environment, sourceClass)) {
            // 占位符替换
            banner = resolver.resolvePlaceholders(banner);
        }
        // 输出
        out.println(banner);
    }
    catch (Exception ex) {
        logger.warn(LogMessage.format("Banner not printable: %s (%s: '%s')", this.resource, ex.getClass(),
                ex.getMessage()), ex);
    }
}
```
