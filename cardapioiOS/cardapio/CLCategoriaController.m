//
//  CACategoriaController.m
//  cardapio
//
//  Created by Julio Rocha on 16/04/14.
//  Copyright (c) 2014 Login. All rights reserved.


#import "CLCategoriaController.h"
#import "CLTitleView.h"
#import "CLItensCardapioBS.h"
#import "CLCollectionViewCell.h"
#import "CLCollectionViewSingleCell.h"
#import "CLCollectionViewNoImageCell.h"
#import "IconDownloader.h"
#import "UIColor+DAHColorUtil.h"
#import "CLTodosItensCardapioBS.h"
#import "CLHeaderView.h"
#import "CLItemController.h"

@interface CLCategoriaController () {
    UIBarButtonItem *rightBarButton;
    BOOL gridView;
    BOOL loading;
    UIRefreshControl *refreshControl;
    NSMutableArray *todosItensMenu;
}

@end

@implementation CLCategoriaController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
    }
    return self;
}

#pragma mark View Configuration

- (void)viewDidLoad {
   
    [super viewDidLoad];
    
    [self initNavigationBar];
    
    self.collectionView.delegate = self;
    
    self.collectionView.dataSource = self;

    [self.collectionView registerNib:[UINib nibWithNibName:@"CLCollectionViewCell" bundle:nil]
          forCellWithReuseIdentifier:@"CLCell"];
    
    [self.collectionView registerNib:[UINib nibWithNibName:@"CLCollectionViewSingleCell" bundle:nil]
          forCellWithReuseIdentifier:@"CLSingleCell"];
    
    [self.collectionView registerNib:[UINib nibWithNibName:@"CLCollectionViewNoImageCell" bundle:nil]
          forCellWithReuseIdentifier:@"CLNoImageCell"];
    
    [self.collectionView registerNib:[UINib nibWithNibName:@"CLHeaderView" bundle:nil] forSupplementaryViewOfKind:UICollectionElementKindSectionHeader withReuseIdentifier:@"CLHeaderView"];
    
    [self configRefreshControl];
    
    if ([self isTodosPratos]) {
        
        gridView = NO;
        
        UICollectionViewFlowLayout *collectionViewLayout = (UICollectionViewFlowLayout*)self.collectionView.collectionViewLayout;
        
        collectionViewLayout.headerReferenceSize = CGSizeMake(self.collectionView.frame.size.width, 50);
        
        [refreshControl beginRefreshing];
        
        [self searchTodosPratos];
        
        return;
        
    }
    
    [self configActionButtons];
    
    if ([self.menu.itens count]==0) {
        
        [refreshControl beginRefreshing];
    
        [self searchItems];
        
    } else {

        [self.collectionView reloadData];
    
    }
    
}

- (void)searchTodosPratos {
    
    if (!loading) {
        
        CLTodosItensCardapioBS *todosItensCardapioBS = [[CLTodosItensCardapioBS alloc]init];
        
        todosItensMenu = [NSMutableArray arrayWithCapacity:0];
        
        UICollectionViewFlowLayout *layout = (UICollectionViewFlowLayout *) self.collectionView.collectionViewLayout;
        
        [layout setSectionInset:UIEdgeInsetsMake(2, 4, 4, 4)];
        
        [todosItensCardapioBS setCompletionHandler:^ {
            
            [refreshControl endRefreshing];
            
            loading = NO;
            
            [self.collectionView reloadData];
            
        }];
        
        loading = YES;
        
        [todosItensCardapioBS getMenus:todosItensMenu];
        
    }
    
}

- (void)searchItems {
    
    if (!loading) {
    
        CLItensCardapioBS *itensCardapioBS = [[CLItensCardapioBS alloc]init];
        
        [itensCardapioBS setCompletionHandler:^ {
            
            [refreshControl endRefreshing];
            
            loading = NO;
            
            [self.collectionView reloadData];
            
        }];
        
        loading = YES;
        
        [itensCardapioBS fillMenuWithItens:self.menu];
        
    }
    
}

- (void)initNavigationBar {
    
    CLTitleView *titleView = [[[NSBundle mainBundle] loadNibNamed:@"CLTitleView" owner:nil options:nil] lastObject];
    
    titleView.titulo = self.menu.descricao;
    
    titleView.imagem = self.menu.imagemTopo;
    
    [titleView configure];
    
    self.navigationItem.titleView = titleView;
    
    self.navigationController.navigationBar.topItem.title = @"";
    
    gridView = YES;
    
}

- (void)configActionButtons {
    
    rightBarButton = [[UIBarButtonItem alloc]initWithImage:[UIImage imageNamed:@"icone_lista"] style:UIBarButtonItemStylePlain target:self action:@selector(changeView)];
    
    self.navigationItem.rightBarButtonItem = rightBarButton;
    
}

- (void)configRefreshControl {
    
    self.collectionView.alwaysBounceVertical = YES;
    
    refreshControl = [[UIRefreshControl alloc] init];
    
    refreshControl.tintColor = [UIColor grayColor];
    
    [refreshControl addTarget:self action:@selector(searchItems) forControlEvents:UIControlEventValueChanged];
    
    [self.collectionView addSubview:refreshControl];
    
}

#pragma mark - ActionButtons

- (void)changeView {
    
    if (gridView) {
    
        rightBarButton.image = [UIImage imageNamed:@"icone_galeria"];

    } else {
    
        rightBarButton.image = [UIImage imageNamed:@"icone_lista"];
    
    }
    
    gridView = !gridView;
    
    if (!loading) {
        
        [UIView animateWithDuration:0.3 animations:^ {

            [self.collectionView reloadData];
            
        }];
        
    }
    
}

#pragma mark - UICollectionView

- (void)tapHeader:(id) sender {
    
    UIView *section = [sender view];
    
    int posicao = (int) section.tag;
    
    CLMenu *categoria = [todosItensMenu objectAtIndex:posicao];
    
    categoria.showItem = !categoria.showItem;
    
    NSMutableArray *array = [[NSMutableArray alloc]init];
    
    for(int i = 0; i < [categoria.itens count]; i++) {
        
        [array addObject:[NSIndexPath indexPathForItem:i inSection:posicao]];
        
    }
    
    if (!categoria.showItem) {
        
        [self.collectionView deleteItemsAtIndexPaths:array];
        
    } else {
        
        [self.collectionView insertItemsAtIndexPaths:array];
        
    }
   
    
}

- (NSInteger)numberOfSectionsInCollectionView:(UICollectionView *)collectionView {
    if ([self isTodosPratos]) {
        return [todosItensMenu count];
    }
    return 1;
}


- (UICollectionReusableView *)collectionView:(UICollectionView *)collectionView viewForSupplementaryElementOfKind:(NSString *)kind atIndexPath:(NSIndexPath *)indexPath
{
    UICollectionReusableView *reusableview = nil;
    
    if (kind == UICollectionElementKindSectionHeader) {
        
        CLHeaderView *headerView = [collectionView dequeueReusableSupplementaryViewOfKind:UICollectionElementKindSectionHeader withReuseIdentifier:@"CLHeaderView" forIndexPath:indexPath];
        
        headerView.tag = indexPath.section;
        
        UITapGestureRecognizer *gestureRecognizer = [[UITapGestureRecognizer alloc]initWithTarget:self action:@selector(tapHeader:)];
        
        [headerView addGestureRecognizer:gestureRecognizer];
        
        [headerView setAlpha:1];
        
        headerView.userInteractionEnabled=YES;

        CLMenu *menu = [todosItensMenu objectAtIndex:indexPath.section];
        
        headerView.titulo.text = menu.descricao;
        
        headerView.imagem.image = [UIImage imageNamed:menu.imagemTopo];
        
        reusableview = headerView;
    }
    
    return reusableview;
}

- (NSInteger)collectionView:(UICollectionView *)collectionView numberOfItemsInSection:(NSInteger)section {

    if ([self isTodosPratos]) {
        
        CLMenu *menu = [todosItensMenu objectAtIndex:section];
        
        if (!menu.showItem) {
            return 0;
        }
        
        return [menu.itens count];
    
    }
    
    return [self.menu.itens count];
    
}

- (UICollectionViewCell *)collectionView:(UICollectionView *)collectionView cellForItemAtIndexPath:(NSIndexPath *)indexPath {
    
    if (!gridView) {
        return [self collectionView:collectionView singleCellForItemAtIndexPath:indexPath];
    }
    
    CLCollectionViewCell *cell = [collectionView dequeueReusableCellWithReuseIdentifier:@"CLCell" forIndexPath:indexPath];
    
    CLItem *item = [self itemAIndexPath:indexPath];
    
    cell.tituloLabel.text = item.nome;
    
    cell.descricaoLabel.text = item.ingredientes;

    cell.imageView.image = [UIImage imageNamed:@"placeholder.png"];
    
    if (item.imagem) {
        
        if (!item.icone) {
            
            [self startIconDownload:item forIndexPath:indexPath];
            
        } else {
            
            cell.imageView.image = item.icone;
            
        }
        
    }
    
    return cell;
}

- (UICollectionViewCell *)collectionView:(UICollectionView *)collectionView singleCellForItemAtIndexPath:(NSIndexPath *)indexPath {
    
    if ([self isTodosPratos]) {
        return [self collectionView:collectionView noImageCellForItemAtIndexPath:indexPath];
    }
    
    CLCollectionViewSingleCell *cell = [collectionView dequeueReusableCellWithReuseIdentifier:@"CLSingleCell" forIndexPath:indexPath];
    
    CLItem *item = [self itemAIndexPath:indexPath];
    
    cell.nomeLabel.text = item.nome;
    
    cell.ingredienteLabel.text = item.ingredientes;
    
    cell.imageView.image = [UIImage imageNamed:@"placeholder.png"];
    
    cell.subItensView.item = item;
    
    [cell.subItensView configure];
    
    if (item.imagem) {
        
        if (!item.icone) {
            
            [self startIconDownload:item forIndexPath:indexPath];
            
        } else {
            
            cell.imageView.image = item.icone;
            
        }
        
    }
    
    return cell;
}

- (UICollectionViewCell *)collectionView:(UICollectionView *)collectionView noImageCellForItemAtIndexPath:(NSIndexPath *)indexPath {
    
    CLCollectionViewNoImageCell *cell = [collectionView dequeueReusableCellWithReuseIdentifier:@"CLNoImageCell" forIndexPath:indexPath];
    
    CLItem *item = [self itemAIndexPath:indexPath];
    
    cell.titulo.text = item.nome;
    
    [cell.titulo sizeToFit];
    
    cell.subItensView.item = item;
    
    [cell.subItensView configure];
    
    return cell;
}


- (CLItem *)itemAIndexPath:(NSIndexPath *)indexPath {
  
    CLItem *item;
    
    if ([self isTodosPratos]) {
        
        CLMenu *menuAtual =  [todosItensMenu objectAtIndex:indexPath.section];
        
        item = [menuAtual.itens objectAtIndex:indexPath.item];
        
    } else {
        
        item = [self.menu.itens objectAtIndex:indexPath.item];
        
    }
    
    return item;
    
}

- (CGSize)collectionView:(UICollectionView *)collectionView layout:(UICollectionViewLayout *)collectionViewLayout sizeForItemAtIndexPath:(NSIndexPath *)indexPath {
    
    if (gridView) {
    
        return CGSizeMake(155,180);
   
    } else {
        
        if ([self isTodosPratos]) {
            
            CLMenu *menu = [todosItensMenu objectAtIndex:indexPath.section];
        
            CLItem *item = [menu.itens objectAtIndex:indexPath.row];
            
            switch ([item.subItens count]) {
                case 0:
                    return CGSizeMake(312, 55);
                    break;
                case 1:
                    return CGSizeMake(312, 55);
                    break;
                case 2:
                    return CGSizeMake(312, 55);
                    break;
                case 3:
                    return CGSizeMake(312, 70);
                    break;
                default:
                    return CGSizeMake(312, 80);
                    break;
            }
            
        }
        
        return CGSizeMake(312,147);
    
    }
    

}


- (void)collectionView:(UICollectionView *)collectionView didSelectItemAtIndexPath:(NSIndexPath *)indexPath {
    
    CLItemController *controller = [[CLItemController alloc]initWithNibName:@"CLItemController" bundle:nil];
    
    if ([self isTodosPratos]) {
        
        CLItem *item = [[[todosItensMenu objectAtIndex:indexPath.section]itens] objectAtIndex:indexPath.row];
        
        controller.item = item;
        
    } else {
        
        CLItem *item = [self.menu.itens objectAtIndex:indexPath.row];
        
        controller.item = item;
        
    }
    
    [self.navigationController pushViewController:controller animated:YES];
    
}


- (void)startIconDownload:(CLItem *)item forIndexPath:(NSIndexPath *)indexPath {
    
    IconDownloader *iconDownloader = [self.imageDownloadsInProgress objectForKey:indexPath];
    
    if (iconDownloader == nil) {
        
        iconDownloader = [[IconDownloader alloc] init];
        
        iconDownloader.item = item;
        
        [iconDownloader setCompletionHandler:^{
            
            CLCollectionViewCell *cell = (CLCollectionViewCell *)[self.collectionView cellForItemAtIndexPath:indexPath];
            
            cell.imageView.image = item.icone;
            
            [self.imageDownloadsInProgress removeObjectForKey:indexPath];
            
        }];
        
        [self.imageDownloadsInProgress setObject:iconDownloader forKey:indexPath];
        
        [iconDownloader startDownload];
        
    }
}

- (void)loadImagesForOnscreenRows {
    
    if ([self.menu.itens count] > 0) {
        
        NSArray *visiblePaths = self.collectionView.indexPathsForVisibleItems;
        
        for (NSIndexPath *indexPath in visiblePaths) {
            
            CLItem *item = [self.menu.itens objectAtIndex:indexPath.item];
            
            if (!item.icone && item.imagem) {
                
                [self startIconDownload:item forIndexPath:indexPath];
                
            }
            
        }
    }
    
}


#pragma mark - UIScrollViewdelegate methods

- (void)scrollViewWillBeginDragging:(UIScrollView *)scrollView {
    [self loadImagesForOnscreenRows];
}


#pragma mark - Auxililar Methods

- (BOOL)isTodosPratos {
    return [self.menu.codigo intValue] == CAMTodosPratos;
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
}
@end
