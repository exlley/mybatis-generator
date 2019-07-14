/**
 *    Copyright 2006-2019 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package mbg.test.mb3.annotated.hierarchical.immutable;

import org.junit.jupiter.api.BeforeEach;

import mbg.test.mb3.AbstractTest;
import mbg.test.mb3.generated.annotated.hierarchical.Immutable.Mapper.FieldsBlobsMapper;
import mbg.test.mb3.generated.annotated.hierarchical.Immutable.Mapper.FieldsOnlyMapper;
import mbg.test.mb3.generated.annotated.hierarchical.Immutable.Mapper.PKBlobsMapper;
import mbg.test.mb3.generated.annotated.hierarchical.Immutable.Mapper.PKFieldsMapper;
import mbg.test.mb3.generated.annotated.hierarchical.Immutable.Mapper.PKFieldsBlobsMapper;
import mbg.test.mb3.generated.annotated.hierarchical.Immutable.Mapper.PKOnlyMapper;

public abstract class AbstractAnnotatedHierarchicalImmutableTest extends AbstractTest {

    @BeforeEach
    @Override
    public void setUp() throws Exception {
        super.setUp();
        sqlSessionFactory.getConfiguration().addMapper(FieldsBlobsMapper.class);
        sqlSessionFactory.getConfiguration().addMapper(FieldsOnlyMapper.class);
        sqlSessionFactory.getConfiguration().addMapper(PKBlobsMapper.class);
        sqlSessionFactory.getConfiguration().addMapper(PKFieldsBlobsMapper.class);
        sqlSessionFactory.getConfiguration().addMapper(PKFieldsMapper.class);
        sqlSessionFactory.getConfiguration().addMapper(PKOnlyMapper.class);
    }
    
    @Override
    public String getMyBatisConfigFile() {
        return "mbg/test/mb3/annotated/MapperConfig.xml";
    }
}
