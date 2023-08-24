import React, { useEffect, useState } from 'react';
import styles from '../../../styles/pages/community/comp/ArticleListShort.module.scss';
import { SelectButton } from 'primereact/selectbutton';
import { Link } from 'react-router-dom';
import classNames from 'classnames/bind';
import { getPopular, getNew } from '../../../client/article';
import { Article } from '../../../redux/reducers/articleReducer';

function ArticleListShort() {
    const cx = classNames.bind(styles);
    const [articleList, setArticleList] = useState<Article[]>([]);
    const postViewOption = ['인기순', '최신순'];
    const [value, setValue] = useState(postViewOption[0]);

    const setSaveValue = (value:string|null) => {
        if (value) {
            setValue(value)
        }
    }
    
    useEffect(() => {
        const getArticleList = async (value: string) => {
            let promise
            if (value === '인기순') {
                promise = getPopular(0, 6)
            } else {
                promise = getNew(0, 6)
            }
            setArticleList((await promise).data)
        }
        getArticleList(value)
    }, [value])

    return (
    <div className={styles.article}>
        <SelectButton value={value} onChange={(e) => setSaveValue(e.value)} options={postViewOption} className={styles.selectButton} />
        
        <div className={styles.articleList}>
            <div className={styles.title}>
                <p className={styles.midTitle}>✨ {value.slice(0,2)} 게시물</p>
                <Link to={'/community/article'} className={styles.moveButton} state={{value:value}}>
                    <p>더보기</p>
                </Link>
            </div>
            { articleList?.map ((article) => (
                <Link to={`/community/article/${article.id}`} key={article.id}>
                    <div className={styles.list}>
                        <p>{ article.title }</p>
                        <div className={styles.numbers}>
                            <div className={styles.numInner}>
                                <i className={cx(styles.icon, 'pi pi-thumbs-up')}></i>
                                <p className={styles.text}>{ article.numLike }</p>
                            </div>
                            <p className={styles.numInner}><i className={cx(styles.icon, 'pi pi-bookmark')}></i>{ article.numScrap }</p>
                        </div>
                    </div>
                </Link>
            ))}
        </div>
    </div>
    );
}

export default ArticleListShort;