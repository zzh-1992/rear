# nacos拉取配置
## org.springframework.boot.context.config.ConfigFileApplicationListener
```java
@Override
public void onApplicationEvent(ApplicationEvent event) {
    if (event instanceof ApplicationEnvironmentPreparedEvent) {
        // 监听ApplicationEnvironmentPreparedEvent
        onApplicationEnvironmentPreparedEvent((ApplicationEnvironmentPreparedEvent) event);
    }
    if (event instanceof ApplicationPreparedEvent) {
        onApplicationPreparedEvent(event);
    }
}
```
```java
private void onApplicationEnvironmentPreparedEvent(ApplicationEnvironmentPreparedEvent event) {
    // 收集环境后置增强器
    List<EnvironmentPostProcessor> postProcessors = loadPostProcessors();
    postProcessors.add(this);
    AnnotationAwareOrderComparator.sort(postProcessors);
    for (EnvironmentPostProcessor postProcessor : postProcessors) {
        // 执行后置增强方法
        postProcessor.postProcessEnvironment(event.getEnvironment(), event.getSpringApplication());
    }
}
```

# com.alibaba.boot.nacos.config.autoconfigure.NacosConfigEnvironmentProcessor
```java
@Override
public void postProcessEnvironment(ConfigurableEnvironment environment,SpringApplication application) {
    application.addInitializers(new NacosConfigApplicationContextInitializer(this));
    nacosConfigProperties = NacosConfigPropertiesUtils.buildNacosConfigProperties(environment);
    // 判断是否使用nacos配置中心
    if (enable()) {
        System.out.println("[Nacos Config Boot] : The preload log configuration is enabled");
        // 加载配置
        loadConfig(environment);
        NacosConfigLoader nacosConfigLoader = NacosConfigLoaderFactory.getSingleton(nacosConfigProperties, environment, builder);
        LogAutoFreshProcess.build(environment, nacosConfigProperties, nacosConfigLoader, builder).process();
    }
}
```
```java
private void loadConfig(ConfigurableEnvironment environment) {
    NacosConfigLoader configLoader = new NacosConfigLoader(nacosConfigProperties,environment, builder);
    configLoader.loadConfig();
    // set defer NacosPropertySource
    deferPropertySources.addAll(configLoader.getNacosPropertySources());
}
```
## com.alibaba.boot.nacos.config.util.NacosConfigLoader
```java
public void loadConfig() {
    MutablePropertySources mutablePropertySources = environment.getPropertySources();
    List<NacosPropertySource> sources = reqGlobalNacosConfig(globalProperties,nacosConfigProperties.getType());
    for (NacosConfigProperties.Config config : nacosConfigProperties.getExtConfig()) {
        List<NacosPropertySource> elements = reqSubNacosConfig(config,
        globalProperties, config.getType());
        sources.addAll(elements);
    }
    if (nacosConfigProperties.isRemoteFirst()) {
        for (ListIterator<NacosPropertySource> itr = sources.listIterator(sources.size()); itr.hasPrevious();) {
            mutablePropertySources.addAfter(
                    StandardEnvironment.SYSTEM_ENVIRONMENT_PROPERTY_SOURCE_NAME, itr.previous());
        }
    } else {
        for (NacosPropertySource propertySource : sources) {
            mutablePropertySources.addLast(propertySource);
        }
    }
}
```

```java
private List<NacosPropertySource> reqGlobalNacosConfig(Properties globalProperties,
        ConfigType type) {
    List<String> dataIds = new ArrayList<>();
    // Loads all data-id information into the list in the list
    if (!StringUtils.hasLength(nacosConfigProperties.getDataId())) {
        final String ids = environment.resolvePlaceholders(nacosConfigProperties.getDataIds());
        dataIds.addAll(Arrays.asList(ids.split(",")));
    }
    else {
        dataIds.add(nacosConfigProperties.getDataId());
    }
    final String groupName = environment.resolvePlaceholders(nacosConfigProperties.getGroup());
    final boolean isAutoRefresh = nacosConfigProperties.isAutoRefresh();
    return new ArrayList<>(Arrays.asList(reqNacosConfig(globalProperties,dataIds.toArray(new String[0]),
        groupName, type, isAutoRefresh)));
}
```

```java
private NacosPropertySource[] reqNacosConfig(Properties configProperties,
        String[] dataIds, String groupId, ConfigType type, boolean isAutoRefresh) {
    final NacosPropertySource[] propertySources = new NacosPropertySource[dataIds.length];
    for (int i = 0; i < dataIds.length; i++) {
        if (!StringUtils.hasLength(dataIds[i])) {
            continue;
        }
        // Remove excess Spaces
        final String dataId = environment.resolvePlaceholders(dataIds[i].trim());
        // 使用工具类请求配置
        final String config = NacosUtils.getContent(builder.apply(configProperties),dataId, groupId);
        final NacosPropertySource nacosPropertySource = new NacosPropertySource(dataId, groupId,
        buildDefaultPropertySourceName(dataId, groupId, configProperties),config, type.getType());
        nacosPropertySource.setDataId(dataId);
        nacosPropertySource.setType(type.getType());
        nacosPropertySource.setGroupId(groupId);
        nacosPropertySource.setAutoRefreshed(isAutoRefresh);
        logger.info("load config from nacos, data-id is : {}, group is : {}",
        nacosPropertySource.getDataId(), nacosPropertySource.getGroupId());
        propertySources[i] = nacosPropertySource;
        DeferNacosPropertySource defer = new DeferNacosPropertySource(
                nacosPropertySource, configProperties, environment);
        nacosPropertySources.add(defer);
    }
    return propertySources;
}
```
## com.alibaba.nacos.spring.util.NacosUtils
```java
public static String getContent(ConfigService configService, String dataId,String groupId) {
    String content = null;
    try {
        // 使用配置类请求
        content = configService.getConfig(dataId, groupId, DEFAULT_TIMEOUT);
    }
    catch (NacosException e) {
        if (logger.isErrorEnabled()) {
            logger.error("Can't get content from dataId : " + dataId + " , groupId : " + groupId, e);
        }
    }
    return content;
}
```
```java
@Override
public String getConfig(String dataId, String group, long timeoutMs) throws NacosException {
    return getConfigInner(namespace, dataId, group, timeoutMs);
}
```
## com.alibaba.nacos.client.config.NacosConfigService
```java
private String getConfigInner(String tenant, String dataId, String group, long timeoutMs) throws NacosException {
group = blank2defaultGroup(group);
        ParamUtils.checkKeyParam(dataId, group);
        ConfigResponse cr = new ConfigResponse();
        cr.setDataId(dataId);
        cr.setTenant(tenant);
        cr.setGroup(group);
        // use local config first
        String content = LocalConfigInfoProcessor.getFailover(worker.getAgentName(), dataId, group, tenant);
        // 当前本地有配置的时候，直接返回
        if (content != null) {
            LOGGER.warn("[{}] [get-config] get failover ok, dataId={}, group={}, tenant={}, config={}",
                worker.getAgentName(), dataId, group, tenant, ContentUtils.truncateContent(content));
            cr.setContent(content);
            String encryptedDataKey = LocalEncryptedDataKeyProcessor
                .getEncryptDataKeyFailover(agent.getName(), dataId, group, tenant);
            cr.setEncryptedDataKey(encryptedDataKey);
            configFilterChainManager.doFilter(null, cr);
            content = cr.getContent();
            return content;
    }
    
    try {
        // 加载远端配置
        ConfigResponse response = worker.getServerConfig(dataId, group, tenant, timeoutMs, false);
        cr.setContent(response.getContent());
        cr.setEncryptedDataKey(response.getEncryptedDataKey());
        configFilterChainManager.doFilter(null, cr);
        content = cr.getContent();
        
        return content;
    } catch (NacosException ioe) {
        if (NacosException.NO_RIGHT == ioe.getErrCode()) {
            throw ioe;
        }
        LOGGER.warn("[{}] [get-config] get from server error, dataId={}, group={}, tenant={}, msg={}",
                worker.getAgentName(), dataId, group, tenant, ioe.toString());
    }
    
    LOGGER.warn("[{}] [get-config] get snapshot ok, dataId={}, group={}, tenant={}, config={}",
            worker.getAgentName(), dataId, group, tenant, ContentUtils.truncateContent(content));
    content = LocalConfigInfoProcessor.getSnapshot(worker.getAgentName(), dataId, group, tenant);
    cr.setContent(content);
    String encryptedDataKey = LocalEncryptedDataKeyProcessor
            .getEncryptDataKeyFailover(agent.getName(), dataId, group, tenant);
    cr.setEncryptedDataKey(encryptedDataKey);
    configFilterChainManager.doFilter(null, cr);
    content = cr.getContent();
    return content;
}
```
## com.alibaba.nacos.client.config.impl.ClientWorker
```java
public ConfigResponse getServerConfig(String dataId, String group, String tenant, long readTimeout, boolean notify)throws NacosException {
		if (StringUtils.isBlank(group)) {
				group = Constants.DEFAULT_GROUP;
  	}
  	// 请求远端配置
  	return this.agent.queryConfig(dataId, group, tenant, readTimeout, notify);
}
```
```java
@Override
public ConfigResponse queryConfig(String dataId, String group, String tenant, long readTimeouts, boolean notify)
throws NacosException {
		ConfigQueryRequest request = ConfigQueryRequest.build(dataId, group, tenant);
  	request.putHeader(NOTIFY_HEADER, String.valueOf(notify));
  	RpcClient rpcClient = getOneRunningClient();
  	if (notify) {
    		CacheData cacheData = cacheMap.get().get(GroupKey.getKeyTenant(dataId, group, tenant));
    if (cacheData != null) {
      	rpcClient = ensureRpcClient(String.valueOf(cacheData.getTaskId()));
    }
  }
  // 请求
  ConfigQueryResponse response = (ConfigQueryResponse) requestProxy(rpcClient, request, readTimeouts);
  ConfigResponse configResponse = new ConfigResponse();
  if (response.isSuccess()) {
    	LocalConfigInfoProcessor.saveSnapshot(this.getName(), dataId, group, tenant, response.getContent());
    	configResponse.setContent(response.getContent());
    	String configType;
    	if (StringUtils.isNotBlank(response.getContentType())) {
      		configType = response.getContentType();
      } else {
        configType = ConfigType.TEXT.getType();
      }
      configResponse.setConfigType(configType);
      String encryptedDataKey = response.getEncryptedDataKey();
      LocalEncryptedDataKeyProcessor
        .saveEncryptDataKeySnapshot(agent.getName(), dataId, group, tenant, encryptedDataKey);
      configResponse.setEncryptedDataKey(encryptedDataKey);
      return configResponse;
  } else if (response.getErrorCode() == ConfigQueryResponse.CONFIG_NOT_FOUND) {
    	LocalConfigInfoProcessor.saveSnapshot(this.getName(), dataId, group, tenant, null);
    	LocalEncryptedDataKeyProcessor.saveEncryptDataKeySnapshot(agent.getName(), dataId, group, tenant, null);
    	return configResponse;
  } else if (response.getErrorCode() == ConfigQueryResponse.CONFIG_QUERY_CONFLICT) {
    	LOGGER.error("[{}] [sub-server-error] get server config being modified concurrently, dataId={}, group={}, 			"+ "tenant={}", this.getName(), dataId, group, tenant);
    	throw new NacosException(NacosException.CONFLICT,"data being modified, dataId=" + dataId + ",group=" + 					group + ",tenant=" + tenant);
  } else {
      LOGGER.error("[{}] [sub-server-error]  dataId={}, group={}, tenant={}, code={}", this.getName(), dataId,
      group, tenant, response);
    	throw new NacosException(response.getErrorCode(),"http error, code=" + response.getErrorCode() + ",msg=" + 				response.getMessage() + ",dataId=" + dataId + ",group=" + group + ",tenant=" + tenant);
  }
}
```
```java
        private Response requestProxy(RpcClient rpcClientInner, Request request, long timeoutMills)
                throws NacosException {
            try {
                request.putAllHeader(super.getSecurityHeaders(resourceBuild(request)));
                request.putAllHeader(super.getCommonHeader());
            } catch (Exception e) {
                throw new NacosException(NacosException.CLIENT_INVALID_PARAM, e);
            }
            JsonObject asJsonObjectTemp = new Gson().toJsonTree(request).getAsJsonObject();
            asJsonObjectTemp.remove("headers");
            asJsonObjectTemp.remove("requestId");
            boolean limit = Limiter.isLimit(request.getClass() + asJsonObjectTemp.toString());
            if (limit) {
                throw new NacosException(NacosException.CLIENT_OVER_THRESHOLD,
                        "More than client-side current limit threshold");
            }
            // 执行rpc请求
            return rpcClientInner.request(request, timeoutMills);
        }
```
## com.alibaba.nacos.common.remote.client.RpcClient
```java
public Response request(Request request, long timeoutMills) throws NacosException {
        int retryTimes = 0;
        Response response;
        Exception exceptionThrow = null;
        long start = System.currentTimeMillis();
        while (retryTimes < RETRY_TIMES && System.currentTimeMillis() < timeoutMills + start) {
            boolean waitReconnect = false;
            try {
                if (this.currentConnection == null || !isRunning()) {
                    waitReconnect = true;
                    throw new NacosException(NacosException.CLIENT_DISCONNECT,
                            "Client not connected, current status:" + rpcClientStatus.get());
                }
                // 最终的http请求
                response = this.currentConnection.request(request, timeoutMills);
                if (response == null) {
                    throw new NacosException(SERVER_ERROR, "Unknown Exception.");
                }
                if (response instanceof ErrorResponse) {
                    if (response.getErrorCode() == NacosException.UN_REGISTER) {
                        synchronized (this) {
                            waitReconnect = true;
                            if (rpcClientStatus.compareAndSet(RpcClientStatus.RUNNING, RpcClientStatus.UNHEALTHY)) 
                            {LoggerUtils.printIfErrorEnabled(LOGGER,"Connection is unregistered, switch server, connectionId = {}, request = {}",currentConnection.getConnectionId(), request.getClass().getSimpleName());
                                switchServerAsync();
                            }
                        }
                    }
                    throw new NacosException(response.getErrorCode(), response.getMessage());
                }
                // return response.
                lastActiveTimeStamp = System.currentTimeMillis();
                return response;
                
            } catch (Exception e) {
                if (waitReconnect) {
                    try {
                        // wait client to reconnect.
                        Thread.sleep(Math.min(100, timeoutMills / 3));
                    } catch (Exception exception) {
                        // Do nothing.
                    }
                }
                LoggerUtils.printIfErrorEnabled(LOGGER, "Send request fail, request = {}, retryTimes = {}, errorMessage = {}",request, retryTimes, e.getMessage());
                exceptionThrow = e;
            }
            retryTimes++;
        }
        
        if (rpcClientStatus.compareAndSet(RpcClientStatus.RUNNING, RpcClientStatus.UNHEALTHY)) {
            switchServerAsyncOnRequestFail();
        }
        
        if (exceptionThrow != null) {
            throw (exceptionThrow instanceof NacosException) ? (NacosException) exceptionThrow
                    : new NacosException(SERVER_ERROR, exceptionThrow);
        } else {
            throw new NacosException(SERVER_ERROR, "Request fail, unknown Error");
        }
    }
```
